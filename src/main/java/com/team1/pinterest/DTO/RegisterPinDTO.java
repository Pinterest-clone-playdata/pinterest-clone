package com.team1.pinterest.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegisterPinDTO {

    private MultipartFile multipartFile;
    private PinForm pinForm;
}
