package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Comment;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDTO {

    @ApiModelProperty(example = "user1")
    private String username;
    @ApiModelProperty(example = "1")
    private Long pinId;
    @ApiModelProperty(example = "1")
    private Long commentId;
    @ApiModelProperty(example = "1")
    private Integer likeNum;
    @ApiModelProperty(example = "comment 입니다.")
    private String content;

    public CommentResponseDTO(Comment comment){
        username = comment.getUser().getUsername();
        pinId = comment.getPin().getId();
        content = comment.getContent();
        commentId = comment.getId();
        likeNum = comment.getCount();
    }
    public static Comment toEntity(final CommentResponseDTO dto){
        return new Comment(dto.content);
    }
}
