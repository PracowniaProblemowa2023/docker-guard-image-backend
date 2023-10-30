package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.AccessType;

@Repository
public interface AccessTypeRepository extends CrudRepository<AccessType,String> {
}
