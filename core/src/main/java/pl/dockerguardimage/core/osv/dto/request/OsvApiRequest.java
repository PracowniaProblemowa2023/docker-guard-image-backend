package pl.dockerguardimage.core.osv.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OsvApiRequest(@JsonProperty("package") OsvPackage osvPackage, String version) {
    public record OsvPackage(String name, String ecosystem) {
    }
}
