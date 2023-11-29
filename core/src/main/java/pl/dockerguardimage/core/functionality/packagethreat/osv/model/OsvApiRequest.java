package pl.dockerguardimage.core.functionality.packagethreat.osv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record OsvApiRequest(@JsonProperty("package") OsvPackage osvPackage, String version) {
    public record OsvPackage(String name, String ecosystem) {
    }
}
