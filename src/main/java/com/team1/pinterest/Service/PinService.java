package com.team1.pinterest.Service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.DTO.PinSearchCondition;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Repository.PinRepository;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                             Long userId,
                             MultipartFile multipartFile) throws IOException {

        User user = findById(userId);

        String fileName = fileProcessService.uploadImage(multipartFile);
        Pin pin = pinRepository.save(new Pin(pinForm.getTitle(),
                pinForm.getContent(),
                pinForm.getRole(),
                user,
                fileName));

        return PinToDTO(pin);
    }

    public List<PinDTO> updatePin(Pin pin, Long userId, Long pinId){
        User user = findById(userId);
        pin.setUser(user);

        validation(pin);
        Pin originalPin = findByPinId(pinId);
        if (originalPin.getUser() != pin.getUser()){
            throw new IllegalArgumentException("작성자만 Pin을 수정할 수 있습니다.");
        }

        if (hasText(pin.getContent())) originalPin.changeContent(pin.getContent());
        if (hasText(pin.getTitle())) originalPin.changeTitle(pin.getTitle());
        if (pin.getRole() != null) originalPin.changeRole(pin.getRole());

        return PinToDTO(originalPin);
    }

    public void deletePin(Long userId, Long pinId){
        User user = findById(userId);
        Pin pin = findByPinId(pinId);

        if (pin.getUser() != user){
            throw new IllegalArgumentException("작성자만 Pin을 삭제할 수 있습니다.");
        }

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
    public Slice<PinDTO> getPinsAtHome(Pageable pageable, PinSearchCondition condition){
        return pinRepository.findAllPinHome(pageable,condition);
    }

    @Transactional(readOnly = true)
    public Slice<PinDTO> getPinsByAuthUser(Pageable pageable, Long userId, PinSearchCondition condition){
        User user = findById(userId);
        return pinRepository.findMyPins(pageable,user,condition);
    }

    // 편의 메서드 //
    private Pin findByPinId(Long pinId) {
        return pinRepository.findById(pinId).orElseThrow(() -> new NotFoundException("not found pin"));
    }

    private void validation(Pin pin) {
        if (pin == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(pin.getUser() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("must have user"));
    }

    private List<PinDTO> PinToDTO(Pin pin) {
        List<PinDTO> list = new ArrayList<>();
        for (Pin attribute : List.of(pin)) {
            PinDTO pinDTO = new PinDTO(attribute);
            pinDTO.setPath(awsS3Service.getFileUrl(pinDTO.getPath()));
            list.add(pinDTO);
        }
        return list;
    }

}
