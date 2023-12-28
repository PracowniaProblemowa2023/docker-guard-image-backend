package pl.dockerguardimage.data.functionality.fileaccess.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccessId;

import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class FileAccessQueryServiceImpl implements FileAccessQueryService {

    private final FileAccessRepository repository;

    @Override
    public Set<FileAccess> getAllByImageScanId(Long imageScanId) {
        return repository.findAllByImageScanId(imageScanId);
    }

    @Override
    public FileAccess getById(FileAccessId id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

}
