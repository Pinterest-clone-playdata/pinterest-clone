package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class CommentDTO {

    @ApiModelProperty(example = "user1")
    private String username;
    @ApiModelProperty(example = "1")
    private Long pinId;
    @ApiModelProperty(example = "comment 입니다.")
    private String content;

    public CommentDTO(Comment comment){
        username = comment.getUser().getUsername();
        pinId = comment.getPin().getId();
        content = comment.getContent();
    }
    public static Comment toEntity(final CommentDTO dto){
        return new Comment(dto.content);
    }
}
