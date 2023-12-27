package pl.dockerguardimage.data.functionality.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.comment.domain.Comment;
import pl.dockerguardimage.data.functionality.common.service.CudServiceImpl;

@Transactional
@Service
public class CommentCudServiceImpl extends CudServiceImpl<CommentRepository, Comment, Long> implements CommentCudService {
    public CommentCudServiceImpl(CommentRepository repository) {
        super(repository);
    }
}
