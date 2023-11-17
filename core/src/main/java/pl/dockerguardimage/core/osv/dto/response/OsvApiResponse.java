package pl.dockerguardimage.core.osv.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OsvApiResponse(@JsonProperty("vulns") List<OsvApiVulnerability> vulnerabilities) {
    @JsonIgnoreProperties(value = {"references", "schema_version"}, ignoreUnknown = true)
    public record OsvApiVulnerability(
            String id,
            String summary,
            String details,
            String modified,
            String published,
            List<String> aliases,
            @JsonProperty("database_specific") DatabaseSpecific databaseSpecific,
            List<OsvAffected> affected) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DatabaseSpecific(String severity) {
    }

    @JsonIgnoreProperties({"references", "schema_version", "ranges", "database_specific"})
    public record OsvAffected(
            List<String> versions,
            @JsonIgnoreProperties(ignoreUnknown = true)
            @JsonProperty("ecosystem_specific") EcosystemSpecific ecosystemSpecific,
            @JsonProperty("package") OsvPackage osvPackage
    ) {
    }

    @JsonIgnoreProperties({"purl"})
    public record OsvPackage(String name, String ecosystem) {
    }

    public record EcosystemSpecific(String severity) {
    }
}

