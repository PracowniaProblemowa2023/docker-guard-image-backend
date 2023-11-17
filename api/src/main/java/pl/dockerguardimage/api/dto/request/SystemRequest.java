package pl.dockerguardimage.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import pl.dockerguardimage.api.functionality.common.annotation.EcosystemEnum;
import pl.dockerguardimage.api.functionality.common.validation.Ecosystem;


public record SystemRequest(
        @JsonProperty("package") @NotEmpty @NotNull String packageName,
        @NotEmpty @NotNull String version,
        @NotEmpty @NotNull @EcosystemEnum(enumClazz = Ecosystem.class) String ecosystem) {
}
