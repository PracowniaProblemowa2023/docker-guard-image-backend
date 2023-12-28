package pl.dockerguardimage.core.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.dockerguardimage.core.validator.annotation.UserHasAccessWriteToImageScan;
import pl.dockerguardimage.core.validator.exception.UserNoAccessException;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.Objects;


public class UserHasAccessWriteToImageScanImpl implements ConstraintValidator<UserHasAccessWriteToImageScan, Long> {

    private final ImageScanQueryService imageScanQueryService;

    public UserHasAccessWriteToImageScanImpl(ImageScanQueryService imageScanQueryService) {
        this.imageScanQueryService = imageScanQueryService;
    }

    @Override
    public void initialize(UserHasAccessWriteToImageScan constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(id)) {
            return true;
        }

        var userId = UserContextHolder.getAuthenticatedUser().id();
        var imageScan = imageScanQueryService.getByIdWithFileAccess(id);
        var hasAccess = imageScan.getAuthor().getId().equals(userId) || imageScan.getFileAccesses().stream().anyMatch(fileAccess -> fileAccess.getAccessType().getName().equals(AccessTypePermission.WRITE) && fileAccess.getUser().getId().equals(userId));
        if (hasAccess) {
            return true;
        }

        throw new UserNoAccessException("annotation.userHasAccessToImageScan");
    }

}
