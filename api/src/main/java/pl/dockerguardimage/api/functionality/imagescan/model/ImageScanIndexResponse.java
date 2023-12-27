package pl.dockerguardimage.api.functionality.imagescan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.time.LocalDateTime;

@Builder
public record ImageScanIndexResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("imageName") String imageName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime date,
        @JsonProperty("result") Result result,
        @JsonProperty("author") String author,
        @JsonProperty("permission") String permission) {
}
