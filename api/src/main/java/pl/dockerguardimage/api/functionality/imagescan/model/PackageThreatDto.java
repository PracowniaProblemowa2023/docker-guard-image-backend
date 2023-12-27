package pl.dockerguardimage.api.functionality.imagescan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record PackageThreatDto (
        @JsonProperty("osvId") String osvId,
        @JsonProperty("summary") String summary,
        @JsonProperty("details") String details,
        ZonedDateTime modified,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        ZonedDateTime published,
        @JsonProperty("severity") String severity,
        @JsonProperty("aliases") String aliases
){}
