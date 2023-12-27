package pl.dockerguardimage.api.functionality.imagescan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ImageScanStateResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("imageName") String name,
        @JsonProperty("state") String state) {
}
