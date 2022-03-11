package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Role;
import lombok.Data;

@Data
public class PinForm {

    private Long userId;
    private String title;
    private String content;
    private Role role;
}
