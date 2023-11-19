package pl.dockerguardimage.core.functionality.imagescan.service;

import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanCreateDTO;
import pl.dockerguardimage.core.functionality.imagescan.dto.ImageScanGetDTO;

public interface ImageScanCreateService {
    ImageScanGetDTO create(ImageScanCreateDTO dto);
}
