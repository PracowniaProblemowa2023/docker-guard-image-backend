package pl.dockerguardimage.api.functionality.common.exception.handler;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.dockerguardimage.api.functionality.common.exception.response.ErrorMessage;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestControllerAdvice(basePackages = "pl.dockerguardimage.api")
public class ApiControllerExceptionHandler {

    private final MessageSource apiMessageSource;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        var errors = new HashSet<ErrorMessage.Error>();
        for (var error : e.getAllErrors()) {
            var errorMessage = getErrorMessage(locale, error);
            if (error instanceof FieldError) {
                errors.add(new ErrorMessage.Error(((FieldError) error).getField(), errorMessage));
                continue;
            }

            errors.add(new ErrorMessage.Error(errorMessage));
        }

        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), apiMessageSource.getMessage("common.validationErrorTitle", null, locale), errors);
    }

    private String getErrorMessage(Locale locale, ObjectError error) {
        try {
            if (Objects.isNull(error.getDefaultMessage())) {
                return apiMessageSource.getMessage(error, locale);
            }

            return apiMessageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()), null, locale);
        } catch (NoSuchMessageException ex) {
            return apiMessageSource.getMessage(error, locale);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        var errors = e.getConstraintViolations().stream()
                .map(err -> new ErrorMessage.Error(err.getPropertyPath().toString(), e.getMessage()))
                .collect(Collectors.toSet());

        return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), apiMessageSource.getMessage("common.validationErrorTitle", null, locale), errors);
    }

}
