package pl.dockerguardimage.security.functionality.user.cache;

import org.springframework.stereotype.Service;
import pl.dockerguardimage.security.functionality.user.model.AuthenticatedUser;

import java.util.*;

@Service
class UserCacheImpl implements UserCache {

    private final Map<String, UserRole> userRoleByUsername = new HashMap<>();

    @Override
    public Set<String> putAndGetUserRole(AuthenticatedUser user, String session) {
        userRoleByUsername.put(user.username(), new UserRole(session, user));
        return user.roles();
    }

    @Override
    public Optional<Set<String>> getRolesByUsername(String username, String session) {
        if (!userRoleByUsername.containsKey(username)) {
            return Optional.empty();
        }

        var userRole = userRoleByUsername.get(username);
        if (!Objects.equals(userRole.sessionId, session)) {
            userRoleByUsername.remove(username);
            return Optional.empty();
        }

        return Optional.of(userRole.authenticatedUser().roles());
    }

    @Override
    public AuthenticatedUser getAuthenticatedUser(String username) {
        return userRoleByUsername.get(username).authenticatedUser;
    }

    private record UserRole(String sessionId, AuthenticatedUser authenticatedUser) {
    }

}
