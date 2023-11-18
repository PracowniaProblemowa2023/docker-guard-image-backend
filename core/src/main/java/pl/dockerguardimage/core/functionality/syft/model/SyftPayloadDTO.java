package pl.dockerguardimage.core.functionality.syft.model;

import lombok.Builder;

@Builder
public record SyftPayloadDTO(String name, String version, String type) {
}
