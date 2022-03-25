package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.UserForm;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Exception.CustomException;
import com.team1.pinterest.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@Profile("test")
class UserServiceTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired UserService userService;
    @Autowired AwsS3Service awsS3Service;
    @Autowired UserRepository userRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("url에서 이름만 빼내지는가")
    public void testGetFileName(){
        String url = "https://pinter-bucket.s3.ap-northeast-2.amazonaws.com/0546d2dd-793e-4862-9344-e44b641eb64d.png";
        String fileName = awsS3Service.getFileName(url);

        assertThat(fileName).isEqualTo("0546d2dd-793e-4862-9344-e44b641eb64d.png");
    }

    @Test
    @DisplayName("User-Create 테스트")
    public void testCreateUser() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image",
                "test2.jpg",
                "image/jpg",
                new FileInputStream("C:\\Users\\skgod\\Desktop\\사진\\아 야스\\test.jpg"));

        UserForm userForm = new UserForm("username", "email", "password");
        userForm.setProfile(image);

        userService.createUser(userForm);
        User user = userRepository.findById(1L).orElseThrow();
        assertThat(user.getUsername()).isEqualTo("username");
    }

    @Test
    @DisplayName("Username 중복 에러 발생 테스트")
    public void testCreateUserBadRequestTest() throws IOException {
        MockMultipartFile image = new MockMultipartFile("image",
                "test2.jpg",
                "image/jpg",
                new FileInputStream("C:\\Users\\skgod\\Desktop\\사진\\아 야스\\test.jpg"));

        UserForm userForm = new UserForm("username", "email", "password");
        userForm.setProfile(image);
        UserForm userForm2 = new UserForm("username", "email", "password");
        userForm.setProfile(image);
        userService.createUser(userForm);

        Assertions.assertThrows(CustomException.class, () -> userService.createUser(userForm2));
    }

    @Test
    @DisplayName("deleteUser 테스트, 잘 삭제가 되는가")
    public void testDeleteUserTest() throws IOException{
        MockMultipartFile image = new MockMultipartFile("image",
                "test2.jpg",
                "image/jpg",
                new FileInputStream("C:\\Users\\skgod\\Desktop\\사진\\아 야스\\test.jpg"));

        UserForm userForm = new UserForm("username", "email", "password");
        userForm.setProfile(image);

        userService.createUser(userForm);

        em.flush();
        em.clear();

        userService.deleteUser(1L,1L);

        long count = userRepository.count();
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("updateUser 테스트, 쿼리 확인용")
    @Rollback(value = false)
    public void testUpdateQueryTest() throws IOException{
        MockMultipartFile image = new MockMultipartFile("image",
                "test2.jpg",
                "image/jpg",
                new FileInputStream("C:\\Users\\skgod\\Desktop\\사진\\아 야스\\test.jpg"));

        UserForm userForm = new UserForm("username", "email", "password");
        userForm.setProfile(image);
        userService.createUser(userForm);

        em.flush();
        em.clear();

        UserForm UpdateForm = new UserForm("updateUsername", "newE", "newP");
        UpdateForm.setProfile(image);
        userService.updateUser(UpdateForm,1L,1L);

        User user = userRepository.findById(1L).orElseThrow();
        assertThat(user.getUsername()).isEqualTo("updateUsername");
        assertThat(user.getEmail()).isEqualTo("newE");
    }
}