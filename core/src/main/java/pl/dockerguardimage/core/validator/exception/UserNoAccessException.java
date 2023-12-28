package pl.dockerguardimage.core.validator.exception;

public class UserNoAccessException extends RuntimeException {
    public UserNoAccessException(String message) {
        super(message);
    }
}
