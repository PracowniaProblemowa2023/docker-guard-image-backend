package pl.dockerguardimage.api.functionality.common.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.dockerguardimage.api.functionality.common.annotation.EcosystemEnum;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EcosystemEnumValidator implements ConstraintValidator<EcosystemEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(EcosystemEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClazz().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(value.toString());
    }
}
