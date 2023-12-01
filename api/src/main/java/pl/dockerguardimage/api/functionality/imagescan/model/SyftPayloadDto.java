package pl.dockerguardimage.api.functionality.imagescan.model;

import java.util.Set;

public record SyftPayloadDto(
        String name,
        String version,
        String type,
        Set<PackageThreatDto> packageThreatsOsv,
        Set<PackageThreatDto> packageThreatsCve
) {}
