package pl.dockerguardimage.data.functionality.imagescan.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;

@Repository
public interface ImageScanRepository extends JpaRepository<ImageScan,Long> {
}
