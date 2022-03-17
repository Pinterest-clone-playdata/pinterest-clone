package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.Service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class FollowerAPI {

    private final FollowerService followerService;

    @PostMapping("user/follow/{userId}")
    public ResponseEntity<?> followUser(@PathVariable("userId") Long userId){
        try {
            Long tempUserId = 1L;
            followerService.save(tempUserId,userId);
            ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("user Follow success").build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();

            return ResponseEntity.badRequest().body(response);
        }

    }

    @DeleteMapping("user/follow/{userId}")
    public ResponseEntity<?> followCancelUser(@PathVariable("userId") Long userId){
        try {
            Long tempUserId = 1L;
            followerService.remove(tempUserId,userId);
            ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("user Follow success").build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();

            return ResponseEntity.badRequest().body(response);
        }
    }
}
