package com.team1.pinterest.DTO.Basic;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

import static lombok.AccessLevel.*;

@Builder
@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Schema(description = "Response DTO test ")
public class ResponseDTO<T> {

    private String message;
    private int status;
    private List<T> data;
}
