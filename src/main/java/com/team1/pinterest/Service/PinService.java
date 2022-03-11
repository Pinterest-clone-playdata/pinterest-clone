package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Repository.PinRepository;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                             MultipartFile multipartFile) throws IOException {

        User user = findById(pinForm.getUserId());
        String fileName = fileProcessService.uploadImage(multipartFile);

        Pin pin = pinRepository.save(new Pin(pinForm.getTitle(),
                pinForm.getContent(),
                pinForm.getRole(),
                user,
                awsS3Service.getFileUrl(fileName)));

        return PinToDTO(pin);
    }

    public void updatePin(Pin pin){

    }


    // 편의 메서드 //

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("must have user"));
    }

    private List<PinDTO> PinToDTO(Pin pin) {
        List<PinDTO> list = new ArrayList<>();
        for (Pin attribute : List.of(pin)) {
            PinDTO pinDTO = new PinDTO(attribute);
            list.add(pinDTO);
        }
        return list;
    }

}
