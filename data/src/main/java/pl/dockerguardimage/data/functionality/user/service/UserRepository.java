package pl.dockerguardimage.data.functionality.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.user.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u left join fetch u.roles")
    Optional<User> findOptByUsername(String toLowerCase);

    @Query("select u from User u")
    User findByUsername(String toLowerCase);
}
