package pl.dockerguardimage.data.functionality.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Transactional(readOnly = true)
class CommentQueryServiceImpl implements CommentQueryService {
}
