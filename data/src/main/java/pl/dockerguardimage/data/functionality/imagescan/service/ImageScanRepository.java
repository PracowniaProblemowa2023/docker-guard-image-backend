package pl.dockerguardimage.data.functionality.imagescan.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.util.Optional;

@Repository
public interface ImageScanRepository extends JpaRepository<ImageScan, Long> {

    @Query("select is from ImageScan is where is.imageName = :imageName")
    Optional<ImageScan> findOptByImageName(@Param("imageName") String imageName);

    @Query("select is from ImageScan is " +
            "left join fetch is.syftPayloads spl " +
            "where is.result = :result")
    Iterable<ImageScan> findByResult(@Param("result") Result result);
}
