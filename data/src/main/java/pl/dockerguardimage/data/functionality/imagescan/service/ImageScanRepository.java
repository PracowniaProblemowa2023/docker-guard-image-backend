package pl.dockerguardimage.data.functionality.imagescan.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.imagescan.domain.ImageScan;
import pl.dockerguardimage.data.functionality.imagescan.domain.Result;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ImageScanRepository extends JpaRepository<ImageScan, Long> {

    @Query("select is from ImageScan is where is.imageName = :imageName")
    Optional<ImageScan> findOptByImageName(@Param("imageName") String imageName);

    @Query("select is from ImageScan is " +
            "left join fetch is.syftPayloads spl " +
            "where is.result = :result")
    Iterable<ImageScan> findByResult(@Param("result") Result result);

    @Query("select is from ImageScan is " +
            "left join is.fileAccesses fa " +
            "left join fa.accessType at " +
            "where is.id = :id")
    Optional<ImageScan> findByIdWithFileAccess(@Param("id") Long id);

    @Query("select is from ImageScan is " +
            "left join fetch is.author a " +
            "left join fetch is.fileAccesses fa " +
            "left join fetch fa.user u " +
            "left join fetch fa.accessType at " +
            "where a.id = :id or u.id = :id ")
    Set<ImageScan> findAllById(@Param("id") Long id);
}
