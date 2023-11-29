package pl.dockerguardimage.core.functionality.cve.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.cve.model.CveEvent;
import pl.dockerguardimage.core.functionality.osv.service.PackageThreatService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;

import java.io.IOException;

@Transactional
@AllArgsConstructor
@Component
public class CveEventListener {

    private final ImageScanQueryService imageScanQueryService;
    private final PackageThreatService service;

    @Async
    @EventListener
    public void handleImageScanCreate(CveEvent event) throws IOException, InterruptedException {
        var imageScan = imageScanQueryService.getById(event.getImageScanId());
        service.createAllByImageScanCve(imageScan);
    }

}
