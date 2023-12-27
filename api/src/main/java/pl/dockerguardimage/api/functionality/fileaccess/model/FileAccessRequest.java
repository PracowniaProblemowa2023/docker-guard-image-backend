package pl.dockerguardimage.api.functionality.fileaccess.model;

import pl.dockerguardimage.data.functionality.accesstype.domain.AccessTypePermission;

public record FileAccessRequest(Long userId, AccessTypePermission permission, Long imageScanId) {
}
