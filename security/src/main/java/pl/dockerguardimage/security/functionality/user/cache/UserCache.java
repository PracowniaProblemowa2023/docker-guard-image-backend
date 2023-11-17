package pl.dockerguardimage.security.functionality.user.cache;

import pl.dockerguardimage.security.functionality.user.model.AuthenticatedUser;

import java.util.Optional;
import java.util.Set;

public interface UserCache {
    Set<String> putAndGetUserRole(AuthenticatedUser user, String session);

    Optional<Set<String>> getRolesByUsername(String username, String session);
}
