package pl.dockerguardimage.core.functionality.imagescan.dto;

import lombok.Builder;

@Builder
public record ImageScanCreateDTO(String image) {

}
