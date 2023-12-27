package pl.dockerguardimage.data.functionality.imagescan.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
class ImageScanQueryServiceImpl implements ImageScanQueryService {

    private final ImageScanRepository repository;

    @Override
    public Optional<ImageScan> getOptByImage(String name) {
        return repository.findOptByImageName(name);
    }

    @Override
    public ImageScan getById(Long imageScanId) {
        return repository.findById(imageScanId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ImageScan getByIdWithFileAccess(Long imageScanId) {
        return repository.findByIdWithFileAccess(imageScanId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Iterable<ImageScan> getAllByResult(Result result) {
        return repository.findByResult(result);
    }

    @Override
    public Set<ImageScan> getAllByUserId(Long id) {
        return repository.findAllById(id);
    }
}
