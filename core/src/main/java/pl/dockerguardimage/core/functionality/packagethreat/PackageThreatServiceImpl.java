package pl.dockerguardimage.core.functionality.packagethreat;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.packagethreat.cve.model.CveApiMapperService;
import pl.dockerguardimage.core.functionality.packagethreat.cve.model.CveApiRequest;
import pl.dockerguardimage.core.functionality.packagethreat.cve.model.CveApiResponse;
import pl.dockerguardimage.core.functionality.packagethreat.cve.service.CveApiClientService;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiMapperService;
import pl.dockerguardimage.core.functionality.packagethreat.osv.model.OsvApiRequest;
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

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Set;

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
        Iterable<ImageScan> imageScans = imageScanQueryService.getByResult(Result.PROGRESS);

        log.debug("Images to scan... ");
        imageScans.forEach(x -> log.debug(x.getImageName()));

        for (ImageScan imageScan : imageScans) {
            String imageName = imageScan.getImageName();
            log.debug("Scanning " + imageName + "...");
            log.debug("Scanning " + imageName + "for osv...");
            this.createAllByImageScanOsv(imageScan);
            log.debug(imageName + " scanned for osv...");
            log.debug("Scanning " + imageName + "for cve...");
            this.createAllByImageScanCve(imageScan);
            log.debug(imageName + " scanned for cve...");
            imageScan.setResult(Result.FINISHED);
            imageScanCudService.update(imageScan);
            log.debug(imageName + " scan successfully finished...");
        }
    }

    @Override
    public void createAllByImageScanOsv(ImageScan imageScan) {

        Set<SyftPayload> payloads = imageScan.getSyftPayloads();

        for (SyftPayload payload : payloads) {

            OsvApiRequest osvApiRequest = OsvApiRequest
                    .builder()
                    .osvPackage(new OsvApiRequest.OsvPackage(getPayloadName(payload), getPayloadType(payload)))
                    .version(payload.getVersion())
                    .build();

            OsvApiResponse osvApiResponse = null;
            try {
                osvApiResponse = osvApiClientService
                        .getOsvVulnerabilityResponse(osvApiRequest);
            } catch (IOException | InterruptedException e) {
                log.debug("ERROR FOR OSV REQUEST: " + osvApiRequest);
            }

            if (osvApiResponse != null && osvApiResponse.vulnerabilities() != null) {

                for (OsvApiResponse.OsvApiVulnerability vulnerability :
                        osvApiResponse.vulnerabilities()) {

                    var packageThreat = new PackageThreatOsv();

                    packageThreat.setOsvId(vulnerability.id());
                    packageThreat.setSummary(vulnerability.summary());
                    packageThreat.setDetails(vulnerability.details());

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
        }
    }

    @Override
    public void createAllByImageScanCve(ImageScan imageScan) {

        Set<SyftPayload> payloads = imageScan.getSyftPayloads();

        for (SyftPayload payload : payloads) {

            OsvApiRequest osvApiRequest = OsvApiRequest
                    .builder()
                    .osvPackage(new OsvApiRequest.OsvPackage(getPayloadName(payload), getPayloadType(payload)))
                    .version(payload.getVersion())
                    .build();

            OsvApiResponse osvApiResponse = null;
            try {
                osvApiResponse = osvApiClientService
                        .getOsvVulnerabilityResponse(osvApiRequest);
            } catch (InterruptedException | IOException e) {
                log.debug("ERROR FOR OSV - CVE REQUEST: " + osvApiRequest);
            }

            if (osvApiResponse != null && osvApiResponse.vulnerabilities() != null) {

                for (OsvApiResponse.OsvApiVulnerability vulnerability :
                        osvApiResponse.vulnerabilities()) {

                    CveApiRequest cveApiRequest = CveApiRequest
                            .builder()
                            .cve(vulnerability.aliases() != null ? getCveAlias(vulnerability) : "")
                            .build();

                    CveApiResponse cveApiResponse = null;
                    try {
                        cveApiResponse = cveApiClientService.getOsvVulnerabilityResponse(cveApiRequest);
                    } catch (IOException | InterruptedException e) {
                        log.debug("ERROR FOR CVE REQUEST: " + osvApiRequest);
                    }

                    if (cveApiResponse != null) {

                        var packageThreat = new PackageThreatCve();

                        packageThreat.setCveId(cveApiResponse.id());
                        packageThreat.setSummary(cveApiResponse.summary());
                        packageThreat.setDetails(cveApiResponse.summary());

                        ZonedDateTime modified = parseToZonedDateTime(cveApiResponse.modified());
                        packageThreat.setModified(modified);

                        ZonedDateTime published = parseToZonedDateTime(cveApiResponse.published());
                        packageThreat.setModified(published);

                        packageThreat.setSeverity(CveApiMapperService.getSeverity(cveApiResponse));

                        packageThreat.setSyftPayload(payload);

                        packageThreatCveCudService.create(packageThreat);

                        payload.addPackageThreatCve(packageThreat);
                    }
                }
                syftPayloadCudService.update(payload);
            }
        }
    }

    private String getPayloadName(SyftPayload syftPayload) {
        String syftName = syftPayload.getName();
        if (syftName.contains("spring-boot")) {
            syftName = "org.springframework.boot:" + syftName;
        } else if (syftName.contains("spring")) {
            syftName = "org.springframework:" + syftName;
        } else if (syftName.contains("log4j")) {
            syftName = "org.apache.logging.log4j:" + syftName;
        }
        return syftName;
    }

    private String getPayloadType(SyftPayload syftPayload) {
        String syftType = syftPayload.getType();
        return switch (syftType) {

            case "java-archive" -> "Maven";
            case "deb" -> "Debian";
            case "python" -> "PyPI";
            case "rpm" -> "Linux";
            default -> syftType;
        };
    }

    private ZonedDateTime parseToZonedDateTime(String dateTimeString) {
        DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        DateTimeFormatter zonedDateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, localDateTimeFormatter);
            return localDateTime.atZone(ZoneId.systemDefault());
        } catch (DateTimeParseException e) {
            try {
                return ZonedDateTime.parse(dateTimeString, zonedDateTimeFormatter);
            } catch (DateTimeParseException ex) {
                throw new DateTimeParseException("Date cannot be parsed: " + dateTimeString, dateTimeString, 0);
            }
        }
    }

    private String getCveAlias(OsvApiResponse.OsvApiVulnerability vulnerability) {
        return vulnerability.
                aliases()
                .stream()
                .filter(x -> x.contains("CVE"))
                .findAny()
                .orElse("");
    }
}

