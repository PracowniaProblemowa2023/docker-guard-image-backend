package pl.dockerguardimage.security.config;

import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import pl.dockerguardimage.data.functionality.role.domain.Role;
import pl.dockerguardimage.data.functionality.user.service.UserQueryService;
import pl.dockerguardimage.security.functionality.user.cache.UserCache;
import pl.dockerguardimage.security.functionality.user.service.UserCreateService;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
class CustomJwtAuthenticationConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final UserQueryService userQueryService;
    private final UserCache userCache;
    private final UserCreateService userCreateService;

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        var username = jwt.getClaim("preferred_username").toString();
        var sessionState = jwt.getClaim("session_state").toString();


        var roles = userCache.getRolesByUsername(username, sessionState);
        if (roles.isPresent()) {
            return getGrantedAuthorities(roles.get());
        }

        var userOpt = userQueryService.getOptByUsername(username);
        if (userOpt.isEmpty()) {
            var user = userCreateService.createUser(jwt);
            return getGrantedAuthorities(userCache.putAndGetUserRole(user, sessionState));
        }

        var userRoles = userOpt.get()
                .getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return getGrantedAuthorities(userRoles);
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(Set<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
