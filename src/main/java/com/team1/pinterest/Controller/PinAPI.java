package com.team1.pinterest.Controller;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.DTO.PinSearchCondition;
import com.team1.pinterest.DTO.Basic.ResponseDTO;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Service.PinService;
import com.team1.pinterest.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("v1")
public class PinAPI {

    private final PinService pinService;

    /**
     * TempUserId = Auth적용시 변경
     * Pin Create API
     * @param request
     * @return
     * @throws IOException
     * Exception = MultipartFile 부재, 용량 초과, 사이즈가 너무 작을 경우, 유저가 없을 경우
     */
    @PostMapping("pin")
    public ResponseEntity<?> createPin(@ModelAttribute @Valid PinForm request) throws IOException {
        Long tempUserId = 1L;
        List<PinDTO> dto = pinService.createPin(request, tempUserId);
        try {
            ResponseDTO<PinDTO> response = ResponseDTO.<PinDTO>builder().data(dto).status(200).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.internalServerError().body(response);
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

    @GetMapping("pin/{pinId}")
    public ResponseEntity<?> getOnePin(@PathVariable("pinId") Long pinId){
        try {
            List<PinDTO> pin = pinService.getOnePin(pinId);
            ResponseDTO<PinDTO> response = ResponseDTO.<PinDTO>builder().status(200).data(pin).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            ResponseDTO<Object> response = ResponseDTO.builder().status(500).message(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("pin/home")
    public ResponseEntity<?> getAllAtHome(@PageableDefault(size = 15) Pageable pageable,
                                          PinSearchCondition condition){
        Slice<PinDTO> pinsAtHome = pinService.getPinsAtHome(pageable, condition);
        return ResponseEntity.ok().body(pinsAtHome);
    }

    @GetMapping("pin/follower")
    public ResponseEntity<?> getFollower(@PageableDefault(size = 15) Pageable pageable){

        Long tempUserId = 1L;
        Slice<PinDTO> pinsAtFollower = pinService.getPinsAtFollower(pageable, tempUserId);
        return ResponseEntity.ok().body(pinsAtFollower);
    }

    @GetMapping("pin/user")
    public ResponseEntity<?> getMyPins(@PageableDefault(size = 15) Pageable pageable,
                                       PinSearchCondition condition
                                       ){
        Long tempUserId = 1L;
        Slice<PinDTO> pins = pinService.getPinsByAuthUser(pageable, tempUserId, condition);
        return ResponseEntity.ok().body(pins);
    }
}
