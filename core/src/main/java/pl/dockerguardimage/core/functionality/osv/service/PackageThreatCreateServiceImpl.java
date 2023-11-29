package pl.dockerguardimage.core.functionality.osv.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.cve.model.CveApiMapperService;
import pl.dockerguardimage.core.functionality.cve.model.CveApiRequest;
import pl.dockerguardimage.core.functionality.cve.model.CveApiResponse;
import pl.dockerguardimage.core.functionality.cve.service.CveApiClientService;
import pl.dockerguardimage.core.functionality.osv.model.OsvApiMapperService;
import pl.dockerguardimage.core.functionality.osv.model.OsvApiRequest;
import pl.dockerguardimage.core.functionality.osv.model.OsvApiResponse;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;
import pl.dockerguardimage.data.functionality.syft.service.SyftPayloadCudService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Transactional
@Service
@AllArgsConstructor
public class PackageThreatCreateServiceImpl implements PackageThreatService {

    private final OsvApiClientService osvApiClientService;
    private final CveApiClientService cveApiClientService;
    private final SyftPayloadCudService syftPayloadCudService;

    @Override
    public void createAllByImageScanOsv(ImageScan imageScan) throws IOException, InterruptedException {

        Set<SyftPayload> payloads = imageScan.getSyftPayloads();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (SyftPayload payload : payloads) {

            OsvApiRequest osvApiRequest = OsvApiRequest
                    .builder()
                    .osvPackage(new OsvApiRequest.OsvPackage(payload.getName(), payload.getType()))
                    .version(payload.getVersion())
                    .build();

            OsvApiResponse osvApiResponse = osvApiClientService
                    .getOsvVulnerabilityResponse(osvApiRequest);

            for (OsvApiResponse.OsvApiVulnerability vulnerability :
                    osvApiResponse.vulnerabilities()) {

                var packageThreat = new PackageThreatOsv();

                packageThreat.setOsvId(vulnerability.id());
                packageThreat.setSummary(vulnerability.summary());
                packageThreat.setDetails(vulnerability.details());

                LocalDateTime modified = LocalDateTime.parse(vulnerability.modified(), formatter);
                packageThreat.setModified(modified);

                LocalDateTime published = LocalDateTime.parse(vulnerability.published(), formatter);
                packageThreat.setModified(published);

                packageThreat.setSeverity(OsvApiMapperService.getSeverityFromVulnerability(vulnerability));

                payload.addPackageThreatOsv(packageThreat);

            }

            syftPayloadCudService.update(payload);
        }

    }

    @Override
    public void createAllByImageScanCve(ImageScan imageScan) throws IOException, InterruptedException {

        Set<SyftPayload> payloads = imageScan.getSyftPayloads();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (SyftPayload payload : payloads) {

            OsvApiRequest osvApiRequest = OsvApiRequest
                    .builder()
                    .osvPackage(new OsvApiRequest.OsvPackage(payload.getName(), payload.getType()))
                    .version(payload.getVersion())
                    .build();

            OsvApiResponse osvApiResponse = osvApiClientService
                    .getOsvVulnerabilityResponse(osvApiRequest);

            for (OsvApiResponse.OsvApiVulnerability vulnerability :
                    osvApiResponse.vulnerabilities()) {

                CveApiRequest cveApiRequest = CveApiRequest
                        .builder()
                        .cve(vulnerability.aliases().get(0))
                        .build();

                CveApiResponse cveApiResponse = cveApiClientService.getOsvVulnerabilityResponse(cveApiRequest);

                var packageThreat = new PackageThreatCve();

                packageThreat.setCveId(cveApiResponse.id());
                packageThreat.setSummary(cveApiResponse.summary());
                packageThreat.setDetails(cveApiResponse.summary());

                LocalDateTime modified = LocalDateTime.parse(cveApiResponse.modified(), formatter);
                packageThreat.setModified(modified);

                LocalDateTime published = LocalDateTime.parse(cveApiResponse.published(), formatter);
                packageThreat.setModified(published);

                packageThreat.setSeverity(CveApiMapperService.getSeverity(cveApiResponse));

                payload.addPackageThreatCve(packageThreat);

            }

            syftPayloadCudService.update(payload);
        }


    }
}
