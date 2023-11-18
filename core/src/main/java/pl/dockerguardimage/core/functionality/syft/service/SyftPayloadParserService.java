package pl.dockerguardimage.core.functionality.syft.service;

import pl.dockerguardimage.core.functionality.syft.model.SyftPayloadDTO;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.util.Set;

public interface SyftPayloadParserService {
    Set<SyftPayloadDTO> parse(ImageScan imageScan);
}
