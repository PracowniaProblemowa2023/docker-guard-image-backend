package pl.dockerguardimage.data.functionality.comment.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.comment.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
