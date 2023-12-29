package pl.dockerguardimage.data.functionality.user.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.user.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query("select u from User u left join fetch u.roles where lower(u.username) = :username")
    Optional<User> findOptByUsername(@Param("username") String username);

    @Query("select u from User u where lower(u.username) = :username")
    User findByUsername(@Param("username") String username);
}
