package pl.dockerguardimage.api;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import pl.dockerguardimage.api.dto.request.SystemRequest;

import java.util.Set;
import java.util.stream.Stream;

public class OsvApiControllerTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    protected static Stream<Arguments> provideTestSystemResponseViolations() {
        return Stream.of(
                Arguments.of(null, "1.2.3", "Eco"),
                Arguments.of("pack", null, "Eco"),
                Arguments.of("pack", "1.2.3", null),

                Arguments.of("", "1.2.3", "Eco"),
                Arguments.of("pack", "", "Eco"),
                Arguments.of("pack", "1.2.3", ""),

                Arguments.of(" ", "1.2.3", "Eco"),
                Arguments.of("pack", " ", "Eco"),
                Arguments.of("pack", "1.2.3", " ")
        );
    }

    @BeforeEach
    void setUpEach() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideTestSystemResponseViolations")
    void testNotNullConstraint(String packageName, String version, String ecosystem) throws InterruptedException {
        SystemRequest systemRequest = new SystemRequest(packageName, version, ecosystem);
        Set<ConstraintViolation<SystemRequest>> violations = validator.validate(systemRequest);
        Assertions.assertFalse(violations.isEmpty());
    }

}
