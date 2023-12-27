package pl.dockerguardimage.data.functionality.fileaccess.service;

import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;

import java.util.Set;

public interface FileAccessQueryService {
    Set<FileAccess> getAllByImageScanId(Long imageScanId);
}
