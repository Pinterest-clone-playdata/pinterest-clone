package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Data
@Getter @Setter
@NoArgsConstructor
public class CommentDTO {

    private Long id;
    private Long userId;
    private Long pinId;
    private String content;

    public CommentDTO(Comment comment){
        id = comment.getId();
        userId = comment.getUser().getId();
        pinId = comment.getPin().getId();
        content = comment.getContent();
    }
    public static Comment toEntity(final CommentDTO dto){
        return new Comment(dto.content);
    }
}
