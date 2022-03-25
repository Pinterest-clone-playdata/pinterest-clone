package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class CommentDTO {

    @ApiModelProperty(example = "1")
    private Long id;
    @ApiModelProperty(example = "1")
    private Long userId;
    @ApiModelProperty(example = "1")
    private Long pinId;
    @ApiModelProperty(example = "comment 입니다.")
    private String content;
    @ApiModelProperty(example = "10")
    private int count;

    public CommentDTO(Comment comment){
        id = comment.getId();
        userId = comment.getUser().getId();
        pinId = comment.getPin().getId();
        content = comment.getContent();
        count = comment.getCount();
    }
    public static Comment toEntity(final CommentDTO dto){
        return new Comment(dto.content);
    }
}
