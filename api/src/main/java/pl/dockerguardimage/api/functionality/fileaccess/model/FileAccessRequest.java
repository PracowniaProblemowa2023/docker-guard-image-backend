package pl.dockerguardimage.api.functionality.fileaccess.model;

import jakarta.validation.constraints.NotNull;
import pl.dockerguardimage.core.validator.annotation.UserHasAccessWriteToImageScan;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;

public record FileAccessRequest(@NotNull Long userId,
                                @NotNull AccessTypePermission permission,
                                @UserHasAccessWriteToImageScan @NotNull Long imageScanId) {
}
