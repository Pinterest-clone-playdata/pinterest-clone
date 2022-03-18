package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PinForm {

    private String title;
    private String content;
    private Role role;

    public PinForm(String title, String content, Role role) {
        this.title = title;
        this.content = content;
        this.role = role;
    }

    public static Pin toEntity(final PinForm form){
        return new Pin(form.title, form.content, form.role);
    }
}
