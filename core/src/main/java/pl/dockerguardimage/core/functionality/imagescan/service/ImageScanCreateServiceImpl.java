package pl.dockerguardimage.core.functionality.imagescan.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanCreateDTO;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanGetDTO;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanCudService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;

@Transactional
@Service
@AllArgsConstructor
class ImageScanCreateServiceImpl implements ImageScanCreateService {

    private final ImageScanCudService imageScanCudService;
    private final ImageScanQueryService imageScanQueryService;

    @Override
    public ImageScanGetDTO create(ImageScanCreateDTO dto) {
        var imageScanOpt = imageScanQueryService.getOptByName(dto.name());
        return null;
    }

}
