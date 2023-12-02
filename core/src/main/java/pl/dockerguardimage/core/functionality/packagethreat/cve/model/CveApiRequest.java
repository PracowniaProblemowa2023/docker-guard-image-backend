package pl.dockerguardimage.core.functionality.packagethreat.cve.model;

import lombok.Builder;

@Builder
public record CveApiRequest(String cve) {
}
