package pl.dockerguardimage.core.functionality.syft.service;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

public interface SyftExecService {
    void execute(String imageName, ImageScan imageScan);
}
