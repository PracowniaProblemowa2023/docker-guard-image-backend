package pl.dockerguardimage.data.functionality.fileaccess.service;

import pl.dockerguardimage.data.functionality.common.service.CudService;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccessId;

public interface FileAccessCudService extends CudService<FileAccess, FileAccessId> {
}
