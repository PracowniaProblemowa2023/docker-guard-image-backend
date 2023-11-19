package pl.dockerguardimage.core.functionality.imagescan.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanCreateDTO;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanGetDTO;
import pl.dockerguardimage.core.functionality.syft.model.SyftEvent;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanCudService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.data.functionality.user.service.UserQueryService;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.UUID;

@Service
@AllArgsConstructor
class ImageScanCreateServiceImpl implements ImageScanCreateService {

    private final ImageScanCudService imageScanCudService;
    private final ImageScanQueryService imageScanQueryService;
    private final UserQueryService userQueryService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ImageScanGetDTO create(ImageScanCreateDTO dto) {
        var imageScanOpt = imageScanQueryService.getOptByName(dto.name());
        var name = imageScanOpt.isPresent() ? dto.name() + UUID.randomUUID() : dto.name();
        var imageScan = new ImageScan();
        imageScan.setImageName(dto.image());
        imageScan.setAuthor(userQueryService.getByUsername(UserContextHolder.getAuthenticatedUser().username()));
        imageScan.setName(name);
        var created = imageScanCudService.create(imageScan);
        applicationEventPublisher.publishEvent(new SyftEvent(imageScan.getId()));
        return ImageScanGetDTO.builder()
                .id(created.getId())
                .name(created.getName())
                .image(created.getImageName())
                .result(created.getResult().toString())
                .build();
    }

}
