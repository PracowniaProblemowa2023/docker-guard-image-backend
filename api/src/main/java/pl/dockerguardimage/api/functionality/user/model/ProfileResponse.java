package pl.dockerguardimage.api.functionality.user.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record ProfileResponse(Long id,
                              String name,
                              String lastname,
                              String username,
                              String email,
                              Set<String> roles) {
}
