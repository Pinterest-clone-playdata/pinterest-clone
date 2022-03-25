package com.team1.pinterest.DTO.Basic;

import com.team1.pinterest.Exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.ResponseEntity;

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

    public static ResponseEntity<ResponseDTO<?>> toResponseEntity(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ResponseDTO.builder()
                        .status(errorCode.getHttpStatus().value())
                        .message(errorCode.getDetail())
                        .build());
    }
}
