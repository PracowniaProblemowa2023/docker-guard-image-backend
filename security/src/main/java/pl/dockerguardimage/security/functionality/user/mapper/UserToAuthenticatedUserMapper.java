package pl.dockerguardimage.security.functionality.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.dockerguardimage.data.functionality.role.domain.Role;
import pl.dockerguardimage.data.functionality.user.domain.User;
import pl.dockerguardimage.security.functionality.user.model.AuthenticatedUser;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserToAuthenticatedUserMapper {

    public static AuthenticatedUser mapToAuthenticatedUser(User created) {
        var userRoles = created.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        return AuthenticatedUser.builder()
                .id(created.getId())
                .username(created.getUsername())
                .firstname(created.getFirstname())
                .lastname(created.getLastname())
                .email(created.getEmail())
                .locale(created.getLocale())
                .roles(userRoles)
                .build();
    }

}
