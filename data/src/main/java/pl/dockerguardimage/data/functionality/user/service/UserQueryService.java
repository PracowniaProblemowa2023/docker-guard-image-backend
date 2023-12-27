package pl.dockerguardimage.data.functionality.user.service;


import pl.dockerguardimage.data.functionality.user.domain.User;

import java.util.Optional;

public interface UserQueryService {
    Optional<User> getOptByUsername(String username);

    User getByUsername(String username);

    User getById(Long userId);
}
