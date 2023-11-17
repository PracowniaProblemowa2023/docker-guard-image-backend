package pl.dockerguardimage.api.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SystemResponse(
        @NotEmpty @NotNull String packageName,
        @NotEmpty @NotNull String version,
        @NotEmpty @NotNull String ecosystem,
        @NotNull List<SystemVulnerability> vulnerabilities) {

    public record SystemVulnerability(String id, String summary, String details, String modified, String published,
                                      String severity, List<String> aliases) {
    }

}
