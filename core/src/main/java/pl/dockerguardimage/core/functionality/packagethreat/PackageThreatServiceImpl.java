package pl.dockerguardimage.core.functionality.packagethreat;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.packagethreat.cve.model.CveApiBatchRequest;
import pl.dockerguardimage.core.functionality.packagethreat.cve.model.CveApiBatchResponse;
import pl.dockerguardimage.core.functionality.packagethreat.cve.model.CveApiMapperService;
import pl.dockerguardimage.core.functionality.packagethreat.cve.model.CveApiRequest;
import pl.dockerguardimage.core.functionality.packagethreat.cve.service.CveApiClientService;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiBatchRequest;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiBatchResponse;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiMapperService;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiResponse;
import pl.dockerguardimage.core.functionality.packagethreat.osv.service.OsvApiClientService;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanCudService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;
import pl.dockerguardimage.data.functionality.packagethreatcve.service.PackageThreatCveCudService;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;
import pl.dockerguardimage.data.functionality.packagethreatosv.service.PackageThreatOsvCudService;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;
import pl.dockerguardimage.data.functionality.syft.service.SyftPayloadCudService;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiMapperService.*;

@Transactional
@Service
@AllArgsConstructor
@Slf4j
public class PackageThreatServiceImpl implements PackageThreatService {

    private final OsvApiClientService osvApiClientService;
    private final CveApiClientService cveApiClientService;
    private final SyftPayloadCudService syftPayloadCudService;
    private final ImageScanQueryService imageScanQueryService;
    private final ImageScanCudService imageScanCudService;
    private final PackageThreatOsvCudService packageThreatOsvCudService;
    private final PackageThreatCveCudService packageThreatCveCudService;

    public void executeImageScanInProgressJob() {
        Iterable<ImageScan> imageScans = imageScanQueryService.getAllByResult(Result.PROGRESS);

        log.debug("Images to scan... ");
        imageScans.forEach(x -> log.debug(x.getImageName()));

        for (ImageScan imageScan : imageScans) {
            String imageName = imageScan.getImageName();
            log.debug("Scanning " + imageName + "...");
            log.debug("Scanning " + imageName + "for osv...");
            Set<OsvApiBatchResponse> osvResponses = this.createAllByImageScanOsv(imageScan);
            log.debug(imageName + " scanned for osv...");
            log.debug("Scanning " + imageName + "for cve...");
            this.createAllByImageScanCve(imageScan, osvResponses);
            log.debug(imageName + " scanned for cve...");
            imageScan.setResult(Result.FINISHED);
            imageScanCudService.update(imageScan);
            log.debug(imageName + " scan successfully finished...");
        }
    }

    @Override
    public Set<OsvApiBatchResponse> createAllByImageScanOsv(ImageScan imageScan) {

        Set<SyftPayload> payloads = imageScan.getSyftPayloads();

        Set<OsvApiBatchRequest> requests = payloads
                .stream()
                .parallel()
                .map(payload -> new OsvApiBatchRequest(payload, mapPayloadToApiRequest(payload)))
                .collect(Collectors.toSet());

        Set<OsvApiBatchResponse> responses = requests
                .stream()
                .parallel()
                .map(request -> {
                    try {
                        return new OsvApiBatchResponse(request.syftPayload(), osvApiClientService
                                .getOsvVulnerabilityResponse((request.request())));
                    } catch (IOException | InterruptedException e) {
                        log.debug("ERROR FOR OSV REQUEST: " + request);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .filter(osvApiResponse -> osvApiResponse.response() != null)
                .filter(osvApiResponse -> osvApiResponse.response().vulnerabilities() != null)
                .collect(Collectors.toSet());

        responses.forEach(response ->
                createOsvPackageThreatForPayload(response.syftPayload(), response.response()));

        return responses;
    }

    @Override
    public void createAllByImageScanCve(ImageScan imageScan, Set<OsvApiBatchResponse> osvApiBatchResponses) {

        Set<CveApiBatchRequest> requests = osvApiBatchResponses
                .stream()
                .parallel()
                .filter(osvRequest -> osvRequest.response() != null)
                .filter(osvRequest -> osvRequest.response().vulnerabilities() != null)
                .flatMap(osvRequest -> {
                    SyftPayload payload = osvRequest.syftPayload();
                    Set<CveApiRequest> cveApiRequests = osvRequest.response()
                            .vulnerabilities()
                            .stream()
                            .parallel()
                            .map(vulnerability-> CveApiRequest
                                            .builder()
                                            .cve(vulnerability.aliases() != null ? getCveAlias(vulnerability) : "")
                                            .build()
                            )
                            .collect(Collectors.toSet());
                    return cveApiRequests
                            .stream()
                            .parallel()
                            .map(batchRequest -> new CveApiBatchRequest(payload, batchRequest));
                })
                .collect(Collectors.toSet());

        Set<CveApiBatchResponse> responses = requests
                .stream()
                .parallel()
                .map(request -> {
                    try {
                        return new CveApiBatchResponse(request.syftPayload(), cveApiClientService
                                .getOsvVulnerabilityResponse((request.request())));
                    } catch (IOException | InterruptedException e) {
                        log.debug("ERROR FOR CVE REQUEST: " + request);
                        return null;
                    }
                }).collect(Collectors.toSet());

        responses
                .stream()
                .filter(Objects::nonNull)
                .filter(response -> response.syftPayload() != null)
                .filter(response -> response.response() != null)
                .collect(groupingBy(CveApiBatchResponse::syftPayload))
                .forEach(this::createCvePackageThreatForPayload);
    }

    private void createOsvPackageThreatForPayload(SyftPayload payload, OsvApiResponse osvApiResponse) {
        for (OsvApiResponse.OsvApiVulnerability vulnerability :
                osvApiResponse.vulnerabilities()) {

            var packageThreat = new PackageThreatOsv();

            packageThreat.setOsvId(vulnerability.id());
            packageThreat.setSummary(getSummaryFromVulnerability(vulnerability));
            packageThreat.setDetails(vulnerability.details());

            packageThreat.setAliases(getAliasesFromVulnerability(vulnerability));

            ZonedDateTime modified = parseToZonedDateTime(vulnerability.modified());
            packageThreat.setModified(modified);

            ZonedDateTime published = parseToZonedDateTime(vulnerability.published());
            packageThreat.setPublished(published);

            packageThreat.setSeverity(OsvApiMapperService.getSeverityFromVulnerability(vulnerability));

            packageThreat.setSyftPayload(payload);

            packageThreatOsvCudService.create(packageThreat);

            payload.addPackageThreatOsv(packageThreat);
        }
        syftPayloadCudService.update(payload);
    }

    private void createCvePackageThreatForPayload(SyftPayload payload, List<CveApiBatchResponse> responses) {

        for (CveApiBatchResponse cveApiResponse : responses) {
            var packageThreat = new PackageThreatCve();

            packageThreat.setCveId(cveApiResponse.response().id());
            packageThreat.setSummary(cveApiResponse.response().summary());
            packageThreat.setDetails(cveApiResponse.response().summary());

            ZonedDateTime modified = parseToZonedDateTime(cveApiResponse.response().modified());
            packageThreat.setModified(modified);

            ZonedDateTime published = parseToZonedDateTime(cveApiResponse.response().published());
            packageThreat.setModified(published);

            packageThreat.setSeverity(CveApiMapperService.getSeverity(cveApiResponse.response()));

            packageThreat.setSyftPayload(payload);

            packageThreatCveCudService.create(packageThreat);

            payload.addPackageThreatCve(packageThreat);
        }

        syftPayloadCudService.update(payload);
    }
}

