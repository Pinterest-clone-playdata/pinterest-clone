package com.team1.pinterest.DTO.Basic;

import lombok.*;

import java.util.List;

import static lombok.AccessLevel.*;

@Builder
@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ResponseDTO<T> {

    private String etc;
    private int statusCode;
    private List<T> data;
}
