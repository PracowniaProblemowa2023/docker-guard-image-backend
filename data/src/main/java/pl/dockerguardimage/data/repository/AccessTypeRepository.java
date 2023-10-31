package pl.dockerguardimage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.AccessType;

@Repository
public interface AccessTypeRepository extends JpaRepository<AccessType,Long> {
}
