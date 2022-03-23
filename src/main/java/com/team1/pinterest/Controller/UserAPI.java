package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.DTO.UserForm;
import com.team1.pinterest.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("v1")
public class UserAPI {

    private final UserService userService;

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal Long authId, @PathVariable("userId") Long userId){

        userService.deleteUser(userId,authId);

        try {
            ResponseDTO<Object> response = ResponseDTO.builder().message("success delete User").status(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().message(e.getMessage()).status(500).build();
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal Long authId,
                                        @PathVariable("userId") Long userId,
                                        @ModelAttribute UserForm form) throws IOException {
        userService.updateUser(form,userId,authId);

        try {
            ResponseDTO<Object> response = ResponseDTO.builder().message("success update User").status(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().message(e.getMessage()).status(500).build();
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
