package com.team1.pinterest.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinSearchCondition {

    private String title;
    private String username;
    private String content;
    private String orderBy;
}
