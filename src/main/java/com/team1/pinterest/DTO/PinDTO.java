package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import lombok.*;

@Getter @Setter
public class PinDTO {

    private String title;
    private String content;
    private String path;
    private Role role;
    private Long userId;
    private int count;

    public PinDTO(Pin pin){
        title = pin.getTitle();
        content = pin.getContent();
        path = pin.getPath();
        role = pin.getRole();
        userId = pin.getUser().getId();
        count = pin.getCount();
    }
}
