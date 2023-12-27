package pl.dockerguardimage.api.functionality.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.dockerguardimage.api.functionality.user.model.ProfileResponse;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

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

}
