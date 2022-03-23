package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.UserForm;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Exception.CustomException;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.team1.pinterest.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final AwsS3Service awsS3Service;
    private final FileProcessService fileProcessService;
    private final UserRepository userRepository;

    public void createUser(UserForm userForm) throws IOException {

        userDuplicationCheck(userForm);
        User user = UserForm.toEntity(userForm);
        if (!userForm.getProfile().isEmpty()){
            String fileName = fileProcessService.uploadImage(userForm.getProfile());
            String path = awsS3Service.getFileUrl(fileName);
            user.pathSetting(path);
        }

        userRepository.save(user);
    }

    public void deleteUser(Long userId, Long loginId){
        validationUser(userId, loginId);

        User user = findById(userId);
        if (user.getPath() != null){
            String fileName = awsS3Service.getFileName(user.getPath());
            fileProcessService.deleteImage(fileName);
        }

        userRepository.delete(user);
    }

    public void updateUser(UserForm form, Long userId, Long loginId) throws IOException {
        if (!userId.equals(loginId)){
            throw new CustomException(INVALID_AUTH_TOKEN);
        }
        User user = findById(userId);

        OriginalFormMultiPartCheck(form, user);

        user.setUsername(form.getUsername());
        user.setPassword(form.getPassword());
        user.setEmail(form.getEmail());

    }

    // == 편의 메서드 == //
    private void validationUser(Long userId, Long loginId) {
        if (!userId.equals(loginId)){
            throw new CustomException(UNAUTHORIZED_COMMENT);
        }
        if (!userRepository.existsById(userId)){
            throw new CustomException(DATA_NOT_FOUND);
        }
    }

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));
    }

    private void userDuplicationCheck(UserForm userForm) {
        if (userRepository.existsByUsername(userForm.getUsername())){
            log.warn("DUPLICATE_RESOURCE 발생");
            throw new CustomException(DUPLICATE_RESOURCE);
        }
    }

    private void OriginalFormMultiPartCheck(UserForm form, User user) throws IOException {
        if(user.getPath() == null){
            if (form.getProfile() != null){
                String fileName = fileProcessService.uploadImage(form.getProfile());
                String path = awsS3Service.getFileUrl(fileName);
                user.pathSetting(path);
            }
        } else {
            if (form.getProfile() != null){
                String fileName = awsS3Service.getFileName(user.getPath());
                fileProcessService.deleteImage(fileName);

                String uploadFileName = fileProcessService.uploadImage(form.getProfile());
                user.pathSetting(awsS3Service.getFileUrl(uploadFileName));
            }
        }
    }

    public User getByCredentials(String email, String password, PasswordEncoder encoder){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(DATA_NOT_FOUND));

        if (user != null && encoder.matches(password, user.getPassword())){
            return user;
        }
        throw new CustomException(PASSWORD_MISMATCH);
    }
}
