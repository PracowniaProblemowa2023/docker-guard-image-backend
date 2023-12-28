package pl.dockerguardimage.data.functionality.comment.service;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.dockerguardimage.data.functionality.comment.domain.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
            "inner join fetch c.author a " +
            "inner join a.imageScans is " +
            "where is.id = :imageScanId " +
            "order by c.date desc ")
    List<Comment> findAllByImageScanId(@Param("imageScanId") Long imageScanId);
}
