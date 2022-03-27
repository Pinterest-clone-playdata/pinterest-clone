package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.DTO.PinSearchCondition;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Exception.CustomException;
import com.team1.pinterest.Repository.PinRepository;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.team1.pinterest.Exception.ErrorCode.*;
import static org.springframework.util.StringUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PinService {

    private final PinRepository pinRepository;
    private final FileProcessService fileProcessService;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;

    public List<PinDTO> createPin(PinForm pinForm,
                             Long userId) throws IOException {

        User user = findById(userId);

        String fileName = fileProcessService.uploadImage(pinForm.getMultipartFile());
        Pin pin = pinRepository.save(new Pin(pinForm.getTitle(),
                pinForm.getContent(),
                pinForm.getRole(),
                user,
                awsS3Service.getFileUrl(fileName)));

        return PinToDTO(pin);
    }

    public List<PinDTO> updatePin(Pin pin, Long userId, Long pinId){
        User user = findById(userId);
        pin.setUser(user);

        validation(pin);
        Pin originalPin = findByPinId(pinId);
        ValidationPinOrder(originalPin, pin.getUser(), "작성자만 Pin을 수정할 수 있습니다.");

        if (hasText(pin.getContent())) originalPin.changeContent(pin.getContent());
        if (hasText(pin.getTitle())) originalPin.changeTitle(pin.getTitle());
        if (pin.getRole() != null) originalPin.changeRole(pin.getRole());

        return PinToDTO(originalPin);
    }

    public void deletePin(Long userId, Long pinId){
        User user = findById(userId);
        Pin pin = findByPinId(pinId);

        ValidationPinOrder(pin, user, "작성자만 Pin을 삭제할 수 있습니다.");

        awsS3Service.deleteFile(pin.getPath());
        pinRepository.delete(pin);
    }

    @Transactional(readOnly = true)
    public List<PinDTO> getOnePin(Long pinId){
        Pin pin = findByPinId(pinId);
        return PinToDTO(pin);
    }

    @Transactional(readOnly = true)
    public Slice<PinDTO> getPinsAtFollower(Pageable pageable, Long userId){
        User user = findById(userId);
        return pinRepository.findFollowersByUser(pageable, user);
    }

    @Transactional(readOnly = true)
    public Slice<PinDTO> getPinsAtHome(){
        return pinRepository.findAllPinHome();
    }

    @Transactional(readOnly = true)
    public Slice<PinDTO> getPinsByAuthUser(Pageable pageable, Long userId, PinSearchCondition condition){
        User user = findById(userId);
        return pinRepository.findMyPins(pageable,user,condition);
    }

    // 편의 메서드 //
    private Pin findByPinId(Long pinId) {
        return pinRepository.findById(pinId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
    }

    private void validation(Pin pin) {
        if (pin == null) {
            log.warn("Entity cannot be null");
            throw new IllegalArgumentException("엔티티는 비어있으면 안됩니다.");
        }

        if(pin.getUser() == null){
            log.warn("Unknown user");
            throw new IllegalArgumentException("유저 정보를 알 수가 없습니다.");
        }
    }

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저는 반드시 존재해야 합니다."));
    }

    private List<PinDTO> PinToDTO(Pin pin) {
        List<PinDTO> list = new ArrayList<>();
        for (Pin attribute : List.of(pin)) {
            PinDTO pinDTO = new PinDTO(attribute);
            list.add(pinDTO);
        }
        return list;
    }

    private void ValidationPinOrder(Pin originalPin, User user2, String s) {
        if (originalPin.getUser() != user2) {
            throw new IllegalArgumentException(s);
        }
    }
}
