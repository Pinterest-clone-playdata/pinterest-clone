package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.DTO.LoginForm;
import com.team1.pinterest.DTO.UserForm;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Security.TokenProvider;
import com.team1.pinterest.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
@Slf4j
public class AuthenticationAPI {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @RequestMapping(
            path = "/auth/signup",
            method = RequestMethod.POST,
            consumes ="multipart/form-data")
    //@PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@Valid @ModelAttribute UserForm form) throws IOException {
        userService.createUser(form);
        ResponseDTO<Object> response = ResponseDTO.builder().message("create User success").status(200).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody LoginForm form){
        User user = userService.getByCredentials(form.getEmail(), form.getPassword(), passwordEncoder);

        String token = tokenProvider.create(user);
        log.info("token 지급 : " + token );
        ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("Login Success").build();
        return ResponseEntity.ok().body(response);
    }
}
