package pl.dockerguardimage.api.functionality.imagescan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record PackageThreatDto (
        @JsonProperty("osvId") String osvId,
        @JsonProperty("summary") String summary,
        @JsonProperty("details") String details,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modified,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime published,
        @JsonProperty("severity") String severity
){}
