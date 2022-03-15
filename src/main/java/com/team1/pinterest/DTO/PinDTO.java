package com.team1.pinterest.DTO;

import com.querydsl.core.annotations.QueryProjection;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import lombok.*;

@Getter @Setter
public class PinDTO {

    private Long id;
    private String title;
    private String content;
    private String path;
    private Role role;
    private String username;
    private int count;

    @QueryProjection
    public PinDTO(Pin pin){
        id = pin.getId();
        title = pin.getTitle();
        content = pin.getContent();
        path = pin.getPath();
        count = pin.getCount();
        username = pin.getUser().getUsername();
    }
}
