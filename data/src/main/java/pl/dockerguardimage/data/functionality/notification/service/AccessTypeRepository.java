package pl.dockerguardimage.data.functionality.notification.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.accesstype.domain.AccessType;

@Repository
public interface AccessTypeRepository extends JpaRepository<AccessType,Long> {
}
