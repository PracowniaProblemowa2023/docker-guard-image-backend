package pl.dockerguardimage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.ImageScan;

@Repository
public interface ImageScanRepository extends JpaRepository<ImageScan,Long> {
}
