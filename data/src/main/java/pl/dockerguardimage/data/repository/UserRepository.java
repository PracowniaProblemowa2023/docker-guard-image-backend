package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
}
