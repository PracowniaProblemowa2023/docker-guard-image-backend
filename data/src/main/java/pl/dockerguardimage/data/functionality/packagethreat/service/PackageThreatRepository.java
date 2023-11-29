package pl.dockerguardimage.data.functionality.packagethreat.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.packagethreat.domain.PackageThreat;

import java.util.Optional;

@Repository
public interface PackageThreatRepository extends JpaRepository<PackageThreat, Long> {

    @Query("select is from PackageThreat is where is.osvId = :osvId")
    Optional<PackageThreat> findOptByName(String osvId);
}
