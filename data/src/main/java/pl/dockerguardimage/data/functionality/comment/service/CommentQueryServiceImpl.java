package pl.dockerguardimage.data.functionality.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.data.functionality.comment.domain.Comment;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentRepository repository;

    @Override
    public List<Comment> getAllByImageScanId(Long imageScanId) {
        return repository.findAllByImageScanId(imageScanId);
    }
}
