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
@ReportAsSingleViolation
public @interface EcosystemEnum {
    Class<? extends Enum<?>> enumClazz();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
