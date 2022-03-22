package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.UserForm;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Exception.CustomException;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.team1.pinterest.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AwsS3Service awsS3Service;
    private final FileProcessService fileProcessService;
    private final UserRepository userRepository;

    public void createUser(UserForm userForm) throws IOException {

        userDuplicationCheck(userForm);

        String fileName = fileProcessService.uploadImage(userForm.getProfile());
        String path = awsS3Service.getFileUrl(fileName);

        User user = UserForm.toEntity(userForm);
        user.pathSetting(path);

        userRepository.save(user);
    }

    private void userDuplicationCheck(UserForm userForm) {
        if (userRepository.existsByUsername(userForm.getUsername())){
            throw new CustomException(DUPLICATE_RESOURCE);
        }
    }
}
