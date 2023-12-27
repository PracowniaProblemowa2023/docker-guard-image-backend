package pl.dockerguardimage.data.functionality.fileaccess.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
class FileAccessQueryServiceImpl implements FileAccessQueryService {
}
