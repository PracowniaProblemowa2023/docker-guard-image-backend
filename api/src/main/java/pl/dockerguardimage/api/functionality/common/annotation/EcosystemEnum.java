package pl.dockerguardimage.api.functionality.common.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import jakarta.validation.constraints.NotNull;
import pl.dockerguardimage.api.functionality.common.validation.EcosystemEnumValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EcosystemEnumValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "Value cannot be null")
@ReportAsSingleViolation
public @interface EcosystemEnum {
    Class<? extends Enum<?>> enumClazz();

    String message() default "must be any of enum Ecosystem";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
