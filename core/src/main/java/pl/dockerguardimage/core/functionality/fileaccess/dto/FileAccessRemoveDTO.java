package pl.dockerguardimage.core.functionality.fileaccess.dto;

import lombok.Builder;

@Builder
public record FileAccessRemoveDTO(Long userId, Long imageScanId) {
}
