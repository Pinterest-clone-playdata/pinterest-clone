package com.team1.pinterest.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginForm {

    @NotBlank(message = "메일을 입력해 주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;
}
