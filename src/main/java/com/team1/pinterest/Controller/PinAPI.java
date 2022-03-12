package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.DTO.RegisterPinDTO;
import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Service.PinService;
import com.team1.pinterest.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("v1")
public class PinAPI {

    private final PinService pinService;
    private final UserService userService;


    /**
     * TempUserId = Auth적용시 변경
     * Pin Create API
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("pin")
    public ResponseEntity<?> createPin(@ModelAttribute RegisterPinDTO request) throws IOException {
        try {
            Long tempUserId = 1L;

            List<PinDTO> dto = pinService.createPin(request.getPinForm(), tempUserId,request.getMultipartFile());
            ResponseDTO<PinDTO> response = ResponseDTO.<PinDTO>builder().data(dto).status(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("pin/{pinId}")
    public ResponseEntity<?> updatePin(@RequestBody PinForm pinForm, @PathVariable("pinId") Long pinId){
        try {
            Long tempUserId = 1L;
            Pin pin = PinForm.toEntity(pinForm);

            List<PinDTO> dto = pinService.updatePin(pin, tempUserId, pinId);
            ResponseDTO<PinDTO> response = ResponseDTO.<PinDTO>builder().data(dto).status(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("pin/{pinId}")
    public ResponseEntity<?> deletePin(@PathVariable("pinId") Long pinId){
        try {
            Long tempUserId = 1L;
            pinService.deletePin(pinId,tempUserId);
            ResponseDTO<Object> response = ResponseDTO.builder().status(200).message("delete complete").build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
