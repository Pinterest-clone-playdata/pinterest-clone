package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PinForm {

    private MultipartFile multipartFile;

    @NotBlank(message = "title must exist")
    private String title;

    @NotBlank(message = "content must exist")
    private String content;

    @NotNull(message = "role must exist")
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
