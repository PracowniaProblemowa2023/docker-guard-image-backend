package pl.dockerguardimage.core.functionality.syft.service;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

import java.util.Set;

public interface SyftService {
    Set<SyftPayload> createAllByImageScan(ImageScan imageScan);
}
