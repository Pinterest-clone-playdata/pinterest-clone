package com.team1.pinterest.DTO;

import com.team1.pinterest.Entitiy.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class UserForm {

    private MultipartFile profile;

    @NotBlank(message = "닉네임은 반드시 들어가야 합니다.")
    @Pattern(regexp = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,10}$\n", message = "닉네임 형식에 맞지 않습니다.")
    private String username;

    @NotBlank(message = "이메일은 반드시 들어가야 합니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 반드시 들어가야 합니다.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,30}$\n", message = "비밀번호 형식에 맞지 않습니다.")
    private String password;

    public static User toEntity(final UserForm form){
        return new User(form.username, form.email, form.password);
    }
}
