package pl.dockerguardimage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.SyftPayload;

@Repository
public interface SyftPayloadRepository extends JpaRepository<SyftPayload,Long> {
}
