package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.FollowerDTO;
import com.team1.pinterest.DTO.FollowerSearchCondition;
import com.team1.pinterest.Service.FileProcessService;
import com.team1.pinterest.Service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class TestAPI {

    private final FollowerService followerService;
    private final FileProcessService processService;

    @GetMapping("v1/follower")
    public ResponseEntity<?> SliceQuerydsl(@PageableDefault(size = 2) Pageable pageable
                                        , FollowerSearchCondition condition){
        Slice<FollowerDTO> search = followerService.search(pageable,condition);

        return ResponseEntity.ok().body(search);
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadFile(@RequestParam("image")MultipartFile multipartFile) throws IOException {
        processService.uploadImage(multipartFile);
        return ResponseEntity.ok().body("ok");
    }

}
