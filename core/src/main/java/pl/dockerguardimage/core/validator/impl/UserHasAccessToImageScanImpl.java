package pl.dockerguardimage.core.validator.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.dockerguardimage.core.validator.annotation.UserHasAccessToImageScan;
import pl.dockerguardimage.core.validator.exception.UserNoAccessException;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.Objects;


public class UserHasAccessToImageScanImpl implements ConstraintValidator<UserHasAccessToImageScan, Long> {

    private final ImageScanQueryService imageScanQueryService;

    public UserHasAccessToImageScanImpl(ImageScanQueryService imageScanQueryService) {
        this.imageScanQueryService = imageScanQueryService;
    }

    @Override
    public void initialize(UserHasAccessToImageScan constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(id)) {
            return true;
        }

        var userId = UserContextHolder.getAuthenticatedUser().id();
        var imageScan = imageScanQueryService.getByIdWithFileAccess(id);
        var hasAccess = imageScan.getAuthor().getId().equals(userId) || imageScan.getFileAccesses().stream().anyMatch(fileAccess -> fileAccess.getUser().getId().equals(userId));
        if (hasAccess) {
            return true;
        }

        throw new UserNoAccessException("annotation.userHasAccessToImageScan");
    }

}
