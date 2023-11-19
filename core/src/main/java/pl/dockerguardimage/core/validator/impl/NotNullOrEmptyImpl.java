package pl.dockerguardimage.core.validator.impl;

import com.google.common.base.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.dockerguardimage.core.validator.annotation.NotNullOrEmpty;


public class NotNullOrEmptyImpl implements ConstraintValidator<NotNullOrEmpty, String> {

    @Override
    public void initialize(NotNullOrEmpty constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !Strings.isNullOrEmpty(value);
    }

}
