package pl.dockerguardimage.core.functionality.fileaccess.service;

import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessDTO;
import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessRemoveDTO;

public interface FileAccessService {
    void process(FileAccessDTO dto);

    void delete(FileAccessRemoveDTO dto);
}
