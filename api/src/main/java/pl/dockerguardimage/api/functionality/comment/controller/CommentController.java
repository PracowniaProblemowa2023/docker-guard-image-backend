package pl.dockerguardimage.api.functionality.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.dockerguardimage.api.functionality.comment.model.CommentRequest;
import pl.dockerguardimage.core.comment.dto.CommentDTO;
import pl.dockerguardimage.core.comment.dto.CommentGetDTO;
import pl.dockerguardimage.core.comment.mapper.CommentMapper;
import pl.dockerguardimage.core.comment.service.CommentService;
import pl.dockerguardimage.core.validator.annotation.UserHasAccessToImageScan;
import pl.dockerguardimage.data.functionality.comment.service.CommentQueryService;
import pl.dockerguardimage.security.functionality.user.context.UserContextHolder;

import java.util.List;

@Validated
@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;
    private final CommentQueryService commentQueryService;

    @PostMapping
    public ResponseEntity<CommentGetDTO> create(@Validated @RequestBody CommentRequest request) {
        var dto = CommentDTO.builder()
                .imageScanId(request.imageScanId())
                .userId(UserContextHolder.getAuthenticatedUser().id())
                .text(request.text())
                .build();

        return ResponseEntity.ok(commentService.create(dto));
    }

    @GetMapping("/{imageScanId}")
    public ResponseEntity<List<CommentGetDTO>> get(@PathVariable @UserHasAccessToImageScan Long imageScanId) {
        var comments = commentQueryService.getAllByImageScanId(imageScanId)
                .stream()
                .map(CommentMapper::map)
                .toList();
        return ResponseEntity.ok(comments);
    }

}
