package pl.dockerguardimage.data.functionality.packagethreatosv.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.packagethreatosv.domain.PackageThreatOsv;

import java.util.Optional;

@Repository
public interface PackageThreatOsvRepository extends JpaRepository<PackageThreatOsv, Long> {

    @Query("select is from PackageThreatOsv is where is.osvId = :osvId")
    Optional<PackageThreatOsv> findOptByName(String osvId);
}
