package com.team1.pinterest.DTO.Basic;

import lombok.*;

import java.util.List;

import static lombok.AccessLevel.*;

@Builder
@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ResponseDTO<T> {

    private String message;
    private int status;
    private List<T> data;
}
