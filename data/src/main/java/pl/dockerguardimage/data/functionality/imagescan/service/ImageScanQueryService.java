package pl.dockerguardimage.data.functionality.imagescan.service;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.util.Optional;

public interface ImageScanQueryService {
    Optional<ImageScan> getOptByName(String name);

    ImageScan getById(Long imageScanId);

    Iterable<ImageScan> getByResult(Result result);
}
