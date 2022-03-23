package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.Service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class CommentLikeAPI {

    private final CommentLikeService commentLikeService;

    @PostMapping("comment-like/{commentId}")
    public ResponseEntity<?> createCommentLike(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal Long userId){

        commentLikeService.createCommentLike(userId,commentId);
        ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("commentLike add success").build();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("comment-like/{commentId}")
    public ResponseEntity<?> deleteCommentLike(@PathVariable("commentId") Long commentId, @AuthenticationPrincipal Long userId){

        commentLikeService.deleteCommentLike(userId,commentId);
        ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("commentLike remove success").build();
        return ResponseEntity.ok().body(response);
    }
}
