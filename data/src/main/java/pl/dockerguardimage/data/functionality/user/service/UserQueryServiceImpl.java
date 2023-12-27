package pl.dockerguardimage.data.functionality.user.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.user.domain.User;

import java.util.Optional;

@Transactional(readOnly = true)
@AllArgsConstructor
@Service
class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> getOptByUsername(String username) {
        return userRepository.findOptByUsername(username.toLowerCase());
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username.toLowerCase());
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(EntityNotFoundException::new);
    }

}
