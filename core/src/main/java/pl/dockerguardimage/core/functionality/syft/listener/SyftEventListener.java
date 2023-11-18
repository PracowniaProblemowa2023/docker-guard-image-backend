package pl.dockerguardimage.core.functionality.syft.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.syft.model.SyftEvent;
import pl.dockerguardimage.core.functionality.syft.service.SyftService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;

@Transactional
@AllArgsConstructor
@Component
public class SyftEventListener {

    private final ImageScanQueryService imageScanQueryService;
    private final SyftService service;

    @Async
    @EventListener
    public void handleImageScanCreate(SyftEvent event) {
        var imageScan = imageScanQueryService.getById(event.getImageScanId());
        service.createAllByImageScan(imageScan);
    }

}
