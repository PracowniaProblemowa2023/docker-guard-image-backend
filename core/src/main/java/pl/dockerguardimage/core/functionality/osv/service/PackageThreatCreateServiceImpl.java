package pl.dockerguardimage.core.functionality.osv.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.osv.model.OsvApiMapperService;
import pl.dockerguardimage.core.functionality.osv.model.OsvApiRequest;
import pl.dockerguardimage.core.functionality.osv.model.OsvApiResponse;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.packagethreat.domain.PackageThreat;
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
    private final SyftPayloadCudService syftPayloadCudService;

    @Override
    public void createAllByImageScan(ImageScan imageScan) throws IOException, InterruptedException {

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

                var packageThreat = new PackageThreat();

                packageThreat.setOsvId(vulnerability.id());
                packageThreat.setSummary(vulnerability.summary());
                packageThreat.setDetails(vulnerability.details());

                LocalDateTime modified = LocalDateTime.parse(vulnerability.modified(), formatter);
                packageThreat.setModified(modified);

                LocalDateTime published = LocalDateTime.parse(vulnerability.published(), formatter);
                packageThreat.setModified(published);

                packageThreat.setSeverity(OsvApiMapperService.getSeverityFromVulnerability(vulnerability));

                payload.addPackageThreat(packageThreat);

            }

            syftPayloadCudService.update(payload);
        }

    }

}
