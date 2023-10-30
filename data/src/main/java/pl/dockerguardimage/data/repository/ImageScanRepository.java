package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.ImageScan;

@Repository
public interface ImageScanRepository extends CrudRepository<ImageScan,String> {
}
