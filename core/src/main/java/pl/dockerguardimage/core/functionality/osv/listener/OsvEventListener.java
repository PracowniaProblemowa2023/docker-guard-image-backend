package pl.dockerguardimage.core.functionality.osv.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.osv.model.OsvEvent;
import pl.dockerguardimage.core.functionality.osv.service.PackageThreatService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;

import java.io.IOException;

@Transactional
@AllArgsConstructor
@Component
public class OsvEventListener {

    private final ImageScanQueryService imageScanQueryService;
    private final PackageThreatService service;

    @Async
    @EventListener
    public void handleImageScanCreate(OsvEvent event) throws IOException, InterruptedException {
        var imageScan = imageScanQueryService.getById(event.getImageScanId());
        service.createAllByImageScan(imageScan);
    }

}
