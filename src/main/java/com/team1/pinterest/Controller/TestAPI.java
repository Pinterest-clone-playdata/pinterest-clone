package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.FollowerDTO;
import com.team1.pinterest.DTO.FollowerSearchCondition;
import com.team1.pinterest.Service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class TestAPI {

    private final FollowerService followerService;

    @GetMapping("v1/follower")
    public ResponseEntity<?> SliceQuerydsl(@PageableDefault(size = 2) Pageable pageable
                                        , FollowerSearchCondition condition){
        Slice<FollowerDTO> search = followerService.search(pageable,condition);

        return ResponseEntity.ok().body(search);
    }
}
