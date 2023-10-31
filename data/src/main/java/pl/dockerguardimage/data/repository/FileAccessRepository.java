package pl.dockerguardimage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.FileAccess;

@Repository
public interface FileAccessRepository extends JpaRepository<FileAccess,Long> {
}
