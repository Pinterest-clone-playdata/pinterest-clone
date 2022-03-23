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

    @NotNull(message = "이미지 파일은 필수입니다.")
    private MultipartFile multipartFile;

    @NotBlank(message = "제목 입력은 필수입니다/")
    private String title;

    @NotBlank(message = "내용 입력은 필수입니다.")
    private String content;

    @NotNull(message = "공개, 비공개를 선택하십시오")
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
