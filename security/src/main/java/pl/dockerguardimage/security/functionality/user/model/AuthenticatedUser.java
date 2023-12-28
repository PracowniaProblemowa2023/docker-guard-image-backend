package pl.dockerguardimage.security.functionality.user.model;

import lombok.Builder;

import java.util.Set;

@Builder
public record AuthenticatedUser(Long id, String username, String firstname, String lastname, String email,
                                String locale,
                                Set<String> roles) {

}
