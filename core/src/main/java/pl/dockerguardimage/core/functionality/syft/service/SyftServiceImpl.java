package pl.dockerguardimage.core.functionality.syft.service;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.notification.service.NotificationService;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanCudService;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;
import pl.dockerguardimage.data.functionality.syft.service.SyftPayloadCudService;

import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
@AllArgsConstructor
public class SyftServiceImpl implements SyftService {

    private final ImageScanCudService imageScanCudService;
    private final SyftPayloadCudService syftPayloadCudService;
    private final SyftExecService syftExecService;
    private final SyftPayloadParserService syftPayloadParserService;
    private final NotificationService notificationService;

    @Override
    public Set<SyftPayload> createAllByImageScan(ImageScan imageScan) {
        syftExecService.execute(imageScan);
        if (!Strings.isNullOrEmpty(imageScan.getErrorMsg())) {
            imageScanCudService.update(imageScan);
            notificationService.scanWithErrors(imageScan);
            return new HashSet<>();
        }

        var dtos = syftPayloadParserService.parse(imageScan);
        if (dtos.isEmpty()) {
            imageScan.setErrorMsg("Parser returned empty list of syft payloads");
            imageScanCudService.update(imageScan);
            return new HashSet<>();
        }

        var syftPayloads = new HashSet<SyftPayload>();
        for (var dto : dtos) {
            var syftPayload = new SyftPayload();
            syftPayload.setName(dto.name());
            syftPayload.setType(dto.type());
            syftPayload.setVersion(dto.version());
            imageScan.addSyftPayload(syftPayload);
            var created = syftPayloadCudService.create(syftPayload);
            syftPayloads.add(created);
        }

        imageScan.setResult(Result.PROGRESS);
        imageScanCudService.update(imageScan);
        return syftPayloads;
    }

}
