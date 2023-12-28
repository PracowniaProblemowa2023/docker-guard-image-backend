package pl.dockerguardimage.core.comment.service;

import pl.dockerguardimage.core.comment.dto.CommentDTO;
import pl.dockerguardimage.core.comment.dto.CommentGetDTO;

public interface CommentService {
    CommentGetDTO create(CommentDTO dto);
}
