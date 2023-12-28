package pl.dockerguardimage.core.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dockerguardimage.core.comment.dto.CommentDTO;
import pl.dockerguardimage.core.comment.dto.CommentGetDTO;
import pl.dockerguardimage.core.comment.mapper.CommentMapper;
import pl.dockerguardimage.data.functionality.comment.domain.Comment;
import pl.dockerguardimage.data.functionality.comment.service.CommentCudService;
import pl.dockerguardimage.data.functionality.imagescan.service.ImageScanQueryService;
import pl.dockerguardimage.data.functionality.user.service.UserQueryService;

@Transactional
@Service
@AllArgsConstructor
class CommentServiceImpl implements CommentService {

    private final CommentCudService commentCudService;
    private final UserQueryService userQueryService;
    private final ImageScanQueryService imageScanQueryService;

    @Override
    public CommentGetDTO create(CommentDTO dto) {
        var user = userQueryService.getById(dto.userId());
        var imageScan = imageScanQueryService.getById(dto.imageScanId());

        var comment = new Comment();
        comment.setText(dto.text());
        comment.setAuthor(user);
        comment.setImageScan(imageScan);
        var created = commentCudService.create(comment);
        return CommentMapper.map(created);
    }

}
