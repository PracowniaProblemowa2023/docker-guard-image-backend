package pl.dockerguardimage.core.functionality.fileaccess.service;

import pl.dockerguardimage.core.functionality.fileaccess.dto.FileAccessDTO;

public interface FileAccessService {
    void process(FileAccessDTO dto);
}
