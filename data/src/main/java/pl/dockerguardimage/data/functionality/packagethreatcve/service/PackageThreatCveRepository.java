package pl.dockerguardimage.data.functionality.packagethreatcve.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve;

import java.util.Optional;

@Repository
public interface PackageThreatCveRepository extends JpaRepository<PackageThreatCve, Long> {

    @Query("select is from PackageThreatCve is where is.cveId = :cveId")
    Optional<pl.dockerguardimage.data.functionality.packagethreatcve.domain.PackageThreatCve> findOptByName(String cveId);
}
