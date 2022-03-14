package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
@Getter @Setter
public class CommentDTO {

    private Long userId;
    private Long pinId;
    private String content;



    public CommentDTO(Comment comment){

        userId = comment.getUser().getId();
        pinId = comment.getPin().getId();
        content = comment.getContent();
    }
}
