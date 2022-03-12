package com.team1.pinterest.DTO;

import lombok.Data;

@Data
public class FollowerSearchCondition {

    private Long followerId;
    private Long followeeId;
    private String followeeName;
}
