package pl.dockerguardimage.core.functionality.fileaccess.dto;

import lombok.Builder;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;

@Builder
public record FileAccessDTO(Long userId, AccessTypePermission permission, Long imageScanId) {
}
