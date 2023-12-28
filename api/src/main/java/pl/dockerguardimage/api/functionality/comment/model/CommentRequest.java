package pl.dockerguardimage.api.functionality.comment.model;

import jakarta.validation.constraints.NotNull;
import pl.dockerguardimage.core.validator.annotation.NotNullOrEmpty;
import pl.dockerguardimage.core.validator.annotation.UserHasAccessToImageScan;

public record CommentRequest(@UserHasAccessToImageScan @NotNull Long imageScanId, @NotNullOrEmpty String text) {

}
