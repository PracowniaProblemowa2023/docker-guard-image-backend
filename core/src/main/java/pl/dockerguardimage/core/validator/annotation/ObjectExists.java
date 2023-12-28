package pl.dockerguardimage.core.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pl.dockerguardimage.core.validator.impl.UserHasAccessToImageScanImpl;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UserHasAccessToImageScanImpl.class)
@Target({TYPE, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, METHOD, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface ObjectExists {

    String message() default "annotation.userHasAccessToImageScan";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
