package pl.dockerguardimage.api.functionality.fileaccess.model;

import pl.dockerguardimage.core.validator.annotation.UserHasAccessWriteToImageScan;

public record FileAccessRemoveRequest(Long userId, @UserHasAccessWriteToImageScan Long imageScanId) {
}
