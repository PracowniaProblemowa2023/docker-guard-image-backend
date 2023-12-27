package pl.dockerguardimage.core.functionality.packagethreat.osv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

@Builder
public record OsvApiRequest(@JsonIgnore SyftPayload syftPayload, @JsonProperty("package") OsvPackage osvPackage, String version) {
    public record OsvPackage(String name, String ecosystem) {
    }
}
