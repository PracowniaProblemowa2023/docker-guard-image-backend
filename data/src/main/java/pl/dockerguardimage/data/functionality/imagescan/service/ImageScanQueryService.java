package pl.dockerguardimage.data.functionality.imagescan.service;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.util.Optional;

public interface ImageScanQueryService {
    Optional<ImageScan> getOptByName(String name);
}
