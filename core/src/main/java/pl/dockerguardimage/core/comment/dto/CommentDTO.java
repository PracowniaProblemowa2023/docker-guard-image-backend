package pl.dockerguardimage.core.comment.dto;

import lombok.Builder;

@Builder
public record CommentDTO(Long imageScanId, String text, Long userId) {
}
