package pl.dockerguardimage.api.functionality.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import pl.dockerguardimage.api.functionality.common.annotation.EcosystemEnum;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EcosystemEnumValidator implements ConstraintValidator<EcosystemEnum, String> {

    @Autowired
    private MessageSource messageSource;

    private List<String> acceptedValues;

    @Override
    public void initialize(EcosystemEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClazz().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            String messageTemplate = messageSource.getMessage("ecosystemEnum.notNull", null, LocaleContextHolder.getLocale());
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
            return false;
        } else if (!acceptedValues.contains(value)) {
            String messageTemplate = messageSource.getMessage("ecosystemEnum.invalid", null, LocaleContextHolder.getLocale());
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(messageTemplate).addConstraintViolation();
            return false;
        }
        return true;
    }
}
