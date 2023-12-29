package pl.dockerguardimage.api.functionality.user.model;

import lombok.Builder;

@Builder
public record UserResponse(Long id, String fullName, String email) {
}
