package pl.dockerguardimage.data.functionality.fileaccess.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccessId;

import java.util.Set;

@Repository
public interface FileAccessRepository extends JpaRepository<FileAccess, FileAccessId> {

    @Query("select fa from FileAccess fa " +
            "left join fetch fa.accessType at " +
            "left join fetch fa.user u " +
            "left join fa.imageScan is " +
            "where is.id = :imageScanId")
    Set<FileAccess> findAllByImageScanId(@Param("imageScanId") Long imageScanId);
}
