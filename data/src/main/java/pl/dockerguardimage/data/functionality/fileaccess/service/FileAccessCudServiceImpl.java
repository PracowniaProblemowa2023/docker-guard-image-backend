package pl.dockerguardimage.data.functionality.fileaccess.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccessId;

@Transactional
@Service
class FileAccessCudServiceImpl extends CudServiceImpl<FileAccessRepository, FileAccess, FileAccessId> implements FileAccessCudService {

    public FileAccessCudServiceImpl(FileAccessRepository repository) {
        super(repository);
    }

}
