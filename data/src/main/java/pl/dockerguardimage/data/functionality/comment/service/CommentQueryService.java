package pl.dockerguardimage.data.functionality.comment.service;

import pl.dockerguardimage.data.functionality.comment.domain.Comment;

import java.util.List;

public interface CommentQueryService {
    List<Comment> getAllByImageScanId(Long imageScanId);
}
