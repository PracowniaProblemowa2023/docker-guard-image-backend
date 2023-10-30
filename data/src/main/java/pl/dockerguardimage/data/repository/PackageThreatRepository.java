package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.PackageThreat;

@Repository
public interface PackageThreatRepository extends CrudRepository<PackageThreat,String> {
}
