package pl.dockerguardimage.data.functionality.imagescan.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
class ImageScanQueryServiceImpl implements ImageScanQueryService {

    private final ImageScanRepository repository;

    @Override
    public Optional<ImageScan> getOptByName(String name) {
        return repository.findOptByName(name);
    }

    @Override
    public ImageScan getById(Long imageScanId) {
        return repository.findById(imageScanId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
