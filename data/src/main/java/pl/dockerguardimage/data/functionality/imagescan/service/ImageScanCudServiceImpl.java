package pl.dockerguardimage.data.functionality.imagescan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

@Service
@Transactional
class ImageScanCudServiceImpl extends CudServiceImpl<ImageScanRepository, ImageScan, Long> implements ImageScanCudService {
    public ImageScanCudServiceImpl(ImageScanRepository repository) {
        super(repository);
    }
}
