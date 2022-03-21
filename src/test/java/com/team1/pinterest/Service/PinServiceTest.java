package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.Entitiy.Role;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Repository.PinRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@Profile("test")
@AutoConfigureMockMvc
@SpringBootTest
class PinServiceTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired EntityManager em;
    @Autowired PinService pinService;
    @Autowired PinRepository pinRepository;

    @Test
    @DisplayName("MockMvc 테스트")
    public void TestMockMvc() throws IOException {
        MockMultipartFile OverSizeImage = new MockMultipartFile("image",
                "test2.jpg",
                "image/jpg",
                new FileInputStream("C:\\Users\\skgod\\Desktop\\사진\\아 야스\\test.jpg"));

        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        em.persist(user1);
        em.persist(user2);

        PinForm pinForm = new PinForm("TITLE1", "content", Role.PUBLIC);
        List<PinDTO> pin = pinService.createPin(pinForm,user1.getId());

        for (PinDTO pinDTO : pin) {
            assertThat(pinDTO.getTitle()).isEqualTo("TITLE1");
        }
    }

    @Test
    @DisplayName("Test Delete 테스트")
    public void TestDelete() throws IOException{
        MockMultipartFile OverSizeImage = new MockMultipartFile("image",
                "test2.jpg",
                "image/jpg",
                new FileInputStream("C:\\Users\\skgod\\Desktop\\사진\\아 야스\\test.jpg"));

        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        em.persist(user1);
        em.persist(user2);

        PinForm pinForm = new PinForm("TITLE1", "content", Role.PUBLIC);
        List<PinDTO> pin = pinService.createPin(pinForm, user1.getId());
        List<PinDTO> pin2 = pinService.createPin(pinForm, user2.getId());

        System.out.println("pin id = " + pin.get(0).getId());

        pinService.deletePin(user1.getId(),1L);
        long count = pinRepository.count();
        assertThat(count).isEqualTo(1);
    }
}