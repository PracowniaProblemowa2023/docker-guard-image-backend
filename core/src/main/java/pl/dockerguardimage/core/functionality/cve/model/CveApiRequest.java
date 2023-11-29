package pl.dockerguardimage.core.functionality.cve.model;

import lombok.Builder;

@Builder
public record CveApiRequest(String cve) {
}
