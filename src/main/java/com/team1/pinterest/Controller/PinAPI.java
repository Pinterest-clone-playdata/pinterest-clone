package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.RegisterPinDTO;
import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.Service.PinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PinAPI {

    private final PinService pinService;

    @PostMapping("v1/pin")
    public ResponseEntity<?> createPin(@ModelAttribute RegisterPinDTO request) throws IOException {
        try {
            List<PinDTO> dto = pinService.createPin(request.getPinForm(), request.getMultipartFile());
            ResponseDTO<PinDTO> response = ResponseDTO.<PinDTO>builder().data(dto).statusCode(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().statusCode(500).etc(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
