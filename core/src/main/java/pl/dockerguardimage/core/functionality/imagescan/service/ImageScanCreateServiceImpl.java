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

@Service
@AllArgsConstructor
class ImageScanCreateServiceImpl implements ImageScanCreateService {

    private final ImageScanCudService imageScanCudService;
    private final ImageScanQueryService imageScanQueryService;
    private final UserQueryService userQueryService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public ImageScanGetDTO create(ImageScanCreateDTO dto) {
        var imageScanOpt = imageScanQueryService.getOptByImage(dto.image());
//        var imageName = imageScanOpt.isPresent() ? dto.image() + UUID.randomUUID() : dto.image();
        var imageName = dto.image();
        var imageScan = new ImageScan();
        imageScan.setImageName(imageName);
        imageScan.setAuthor(userQueryService.getByUsername(UserContextHolder.getAuthenticatedUser().username()));
        var created = imageScanCudService.create(imageScan);
        applicationEventPublisher.publishEvent(new SyftEvent(imageScan.getId()));
        return ImageScanGetDTO.builder()
                .id(created.getId())
                .image(created.getImageName())
                .result(created.getResult().toString())
                .build();
    }

}
