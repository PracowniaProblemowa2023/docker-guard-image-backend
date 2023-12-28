package pl.dockerguardimage.core.comment.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.dockerguardimage.core.comment.dto.CommentGetDTO;
import pl.dockerguardimage.data.functionality.comment.domain.Comment;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static CommentGetDTO map(Comment comment) {
        return CommentGetDTO.builder()
                .id(comment.getId())
                .date(comment.getDate())
                .text(comment.getText())
                .createdBy(comment.getAuthor().getFullName())
                .build();
    }

}
