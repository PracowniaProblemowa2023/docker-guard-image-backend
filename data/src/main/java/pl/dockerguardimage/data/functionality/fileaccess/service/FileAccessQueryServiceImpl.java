package pl.dockerguardimage.data.functionality.fileaccess.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;

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

}
