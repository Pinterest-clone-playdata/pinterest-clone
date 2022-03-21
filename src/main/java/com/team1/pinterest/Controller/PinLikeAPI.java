package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class PinLikeAPI {

    private final LikeService likeService;

    @PostMapping("pin/{pinId}/like")
    public ResponseEntity<?> likePin(@PathVariable("pinId") Long pinId){
        Long tempUserId = 1L;
        likeService.addLike(tempUserId,pinId);

        try {
            ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("pinLike success").build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("pin/{pinId}/like")
    public ResponseEntity<?> DeleteLikePin(@PathVariable("pinId") Long pinId){
        Long tempUserId = 1L;
        likeService.removeLike(tempUserId,pinId);

        try {
            ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("pinLike Delete Successs").build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
