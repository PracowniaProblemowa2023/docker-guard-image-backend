package pl.dockerguardimage.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment,String> {
}
