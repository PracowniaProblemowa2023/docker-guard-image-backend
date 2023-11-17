package pl.dockerguardimage.security.functionality.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.role.service.RoleQueryService;
import pl.dockerguardimage.data.functionality.user.domain.User;
import pl.dockerguardimage.data.functionality.user.service.UserCudService;
import pl.dockerguardimage.security.functionality.role.helper.RoleHelper;
import pl.dockerguardimage.security.functionality.user.mapper.UserToAuthenticatedUserMapper;
import pl.dockerguardimage.security.functionality.user.model.AuthenticatedUser;

import java.util.Collections;

@Service
@Transactional
@AllArgsConstructor
public class UserCreateServiceImpl implements UserCreateService {

    private final UserCudService userCudService;
    private final RoleQueryService roleQueryService;

    @Override
    public AuthenticatedUser create(Jwt jwt) {
        var username = jwt.getClaim("preferred_username").toString();
        var name = jwt.getClaim("given_name").toString();
        var lastName = jwt.getClaim("family_name").toString();
        var email = jwt.getClaim("email").toString();

        var roles = roleQueryService.getAllByNames(Collections.singleton(RoleHelper.ROLE_DEFAULT));
        var user = new User();
        user.setEmail(email);
        user.setFirstname(name);
        user.setLastname(lastName);
        user.setUsername(username);
        user.addRoles(roles);
        var created = userCudService.create(user);

        return UserToAuthenticatedUserMapper.mapToAuthenticatedUser(created);
    }


}
