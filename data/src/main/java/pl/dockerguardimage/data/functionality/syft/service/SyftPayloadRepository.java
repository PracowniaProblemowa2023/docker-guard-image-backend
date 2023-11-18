package pl.dockerguardimage.data.functionality.syft.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.syft.domain.SyftPayload;

@Repository
public interface SyftPayloadRepository extends JpaRepository<SyftPayload,Long> {
}
