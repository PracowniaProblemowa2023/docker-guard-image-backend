package pl.dockerguardimage.data.functionality.fileaccess.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccess;
import pl.dockerguardimage.data.functionality.fileaccess.domain.FileAccessId;

@Repository
public interface FileAccessRepository extends JpaRepository<FileAccess, FileAccessId> {
}
