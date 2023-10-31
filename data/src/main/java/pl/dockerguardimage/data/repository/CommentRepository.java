package pl.dockerguardimage.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
