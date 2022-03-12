package com.team1.pinterest.DTO;

import com.querydsl.core.annotations.QueryProjection;
import com.team1.pinterest.Entitiy.Follower;
import lombok.Data;

@Data
public class FollowerDTO {

    private Long FollowerId;
    private Long FolloweeId;
    private String FolloweeName;

    @QueryProjection
    public FollowerDTO(Follower follower) {
        FollowerId = follower.getFollower().getId();
        FolloweeId = follower.getFollowee().getId();
        FolloweeName = follower.getFollowee().getUsername();
    }
}
