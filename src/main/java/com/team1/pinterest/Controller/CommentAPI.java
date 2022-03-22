package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.DTO.CommentDTO;
import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Service.CommentService;
import com.team1.pinterest.Service.PinService;
import com.team1.pinterest.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "코멘트 등록 기능", description = "유저가 핀에서 코멘트를 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "comment 등록 성공!", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })

    //@PostMapping("/pin/{pinId}/comment")
    @PostMapping("/pin-comment/{pinId}/comment")
    public ResponseEntity<?> createComment( @RequestBody CommentDTO request, @Parameter(description = "comment가 생성 될 pin 의 아이디를 입력해주세요.", required = true, example = "1") @PathVariable("pinId") Long pinId ) throws IOException{

        Long tempUserId = 1L;
        List<CommentDTO> dto = commentService.createComment(request.getUserId(),pinId, request.getContent());
        return getResponseEntity(dto);
    }

    @GetMapping("/pin-comment/{pinId}/comment")
    public ResponseEntity<?> getCommentAllAtPin(@PathVariable("pinId")Long piniId){
        List<CommentDTO> dto  = commentService.readByPinId(piniId);
        return getResponseEntity(dto);
    }
    @GetMapping("/pin-comment/{pinId}/comment/{commentId}")
    public ResponseEntity<?> getOneComment(@PathVariable("pinId") Long pinId, @PathVariable("commentId") Long commentId){
        List<CommentDTO> dto = commentService.readById(commentId);
        return getResponseEntity(dto);

    }
    @PatchMapping("/pin-comment/{pinId}/comment/{commentId}")
    public ResponseEntity<?> updateComment(@RequestBody CommentDTO commentDTO,@PathVariable("pinId") Long pinId, @PathVariable("commentId") Long commentId){
        Long userId = 1L; //로그인 설정 후 변경
        Comment comment  = CommentDTO.toEntity(commentDTO);
        List<CommentDTO> dto = commentService.updateComment(comment,userId,pinId,commentId);
        return getResponseEntity(dto);
    }
    @DeleteMapping("/pin-comment/{pinId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") Long commentId){
        Long userId = 1L; //로그인 설정 후 변경
        commentService.deleteComment(userId,commentId);
        try {

            ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("delete complete").build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    //
    private ResponseEntity<?> getResponseEntity(List<CommentDTO> dto) {
        try{
            ResponseDTO<CommentDTO> response = ResponseDTO.<CommentDTO>builder().data(dto).status(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.internalServerError().body(response);
        }
    }

}