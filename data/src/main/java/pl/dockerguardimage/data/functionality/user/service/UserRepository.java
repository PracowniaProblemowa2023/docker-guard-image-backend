package pl.dockerguardimage.data.functionality.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
