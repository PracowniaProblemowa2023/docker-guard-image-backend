package pl.dockerguardimage.api.functionality.imagescan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ImageScanResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("imageName") String imageName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,
        @JsonProperty("result") Result result,
        @JsonProperty("permission") AccessTypePermission permission,
        @JsonProperty("author") String author,
        @JsonProperty("payloads") Set<SyftPayloadDto> payloads
) {
}
