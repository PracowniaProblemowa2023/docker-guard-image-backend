package pl.dockerguardimage.security.functionality.user.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record AuthenticatedUser(String username, Set<String> roles) {

}
