package pl.dockerguardimage.data.functionality.imagescan.service;

import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.util.Set;

public interface ImageScanQueryService {

    ImageScan getById(Long imageScanId);

    ImageScan getByIdWithFileAccess(Long imageScanId);

    Iterable<ImageScan> getAllByResult(Result result);

    Set<ImageScan> getAllByUserId(Long id);
}
