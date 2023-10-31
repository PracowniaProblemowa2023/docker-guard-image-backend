package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.FileAccess;

@Repository
public interface FileAccessRepository extends CrudRepository<FileAccess,String> {
}