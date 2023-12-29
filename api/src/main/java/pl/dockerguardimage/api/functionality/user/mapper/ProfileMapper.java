package pl.dockerguardimage.api.functionality.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import pl.dockerguardimage.api.functionality.user.model.ProfileResponse;
import pl.dockerguardimage.api.functionality.user.model.UserResponse;
import pl.dockerguardimage.data.functionality.user.domain.User;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileMapper {

    public static ProfileResponse map() {
        var authenticatedUser = UserContextHolder.getAuthenticatedUser();
        return ProfileResponse
                .builder()
                .id(authenticatedUser.id())
                .name(authenticatedUser.firstname())
                .lastname(authenticatedUser.lastname())
                .username(authenticatedUser.username())
                .email(authenticatedUser.email())
                .roles(authenticatedUser.roles())
                .build();
    }

    public static List<UserResponse> map(Page<User> users) {
        return users.stream().map(user ->
                UserResponse.builder()
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .id(user.getId())
                        .build()
        ).collect(Collectors.toList());
    }
}
