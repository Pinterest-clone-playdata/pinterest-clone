package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.DTO.CommentDTO;
import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.Service.CommentService;
import com.team1.pinterest.Service.PinService;
import com.team1.pinterest.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor //Private final
@Slf4j
@RequestMapping("v1")
public class CommentAPI {

    private final PinService pinService;
    private final CommentService commentService;
    private final UserService userService;

    /**
     * TempUserId = Auth적용시 변경
     * Pin Create API
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/pin/{pinId}/comment")
    public ResponseEntity<?> createComment( @RequestBody CommentDTO request, @PathVariable("pinId") Long pinId ){
        try{
            Long tempUserId = 1L;
            List<CommentDTO> dto = commentService.createComment(request.getUserId(),pinId, request.getContent());
            ResponseDTO<CommentDTO> response =ResponseDTO.<CommentDTO>builder().data(dto).status(200).build();
            return ResponseEntity.ok().body(response);


        } catch (IOException e) {
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/pin/{pinId}/comment")
    public ResponseEntity<?> getCommentAllAtPin(@PathVariable("pinId")Long piniId){
        try {
            List<CommentDTO> dto  = commentService.readByPinId(piniId);
            ResponseDTO<CommentDTO> response = ResponseDTO.<CommentDTO>builder().status(200).data(dto).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/pin/{pinId}/comment/{commentId}")
    public ResponseEntity<?> getOneComment(@PathVariable("pinId") Long pinId, @PathVariable("commentId") Long commentId){

        try {
            List<CommentDTO> dto = commentService.readById(commentId);
            ResponseDTO<CommentDTO> response =ResponseDTO.<CommentDTO>builder().data(dto).status(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}