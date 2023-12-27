package pl.dockerguardimage.core.functionality.imagescan.dto;

import lombok.Builder;

@Builder
public record ImageScanGetDTO(Long id, String image, String result) {

}
