package pl.dockerguardimage.data.functionality.imagescan.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

import java.util.Optional;

@Repository
public interface ImageScanRepository extends JpaRepository<ImageScan, Long> {

    @Query("select is from ImageScan is where is.name = :name")
    Optional<ImageScan> findOptByName(@Param("name") String name);
}
