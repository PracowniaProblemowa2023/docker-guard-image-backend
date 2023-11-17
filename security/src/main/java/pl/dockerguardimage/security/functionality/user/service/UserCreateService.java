package pl.dockerguardimage.security.functionality.user.service;

import org.springframework.security.oauth2.jwt.Jwt;
import pl.dockerguardimage.security.functionality.user.model.AuthenticatedUser;

public interface UserCreateService {
    AuthenticatedUser create(Jwt jwt);
}
