package pl.dockerguardimage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.PackageThreat;

@Repository
public interface PackageThreatRepository extends JpaRepository<PackageThreat,String> {
}
