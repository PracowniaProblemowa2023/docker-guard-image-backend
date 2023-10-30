package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.SyftPayload;

@Repository
public interface SyftPayloadRepository extends CrudRepository<SyftPayload,String> {
}
