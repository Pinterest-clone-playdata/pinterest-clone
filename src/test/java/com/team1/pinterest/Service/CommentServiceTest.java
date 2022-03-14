package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.CommentDTO;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import com.team1.pinterest.Entitiy.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Profile("test")
@AutoConfigureMockMvc
@SpringBootTest
class CommentServiceTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired
    EntityManager em;
    @Autowired PinService pinService;
    @Autowired CommentService commentService;

    @Test
    //@Rollback(value = false)
    void createComment() throws IOException {

        User user1 = new User("홍길동","111@gmail.com","111");
        em.persist(user1);

        Pin pin1 = new Pin("스타트렉","대머리 아조씨", Role.PUBLIC,user1,"/asdad/asdkalsd.jpg");
        em.persist(pin1);
        User user2 = new User("고길동","111@gmail.com","111");
        em.persist(user2);

        List<CommentDTO> serviceComment = commentService.createComment(user2.getId(), pin1.getId(), "자라나라 머리머리");
        // 데이터 베이스에 정상적으로 저장 확인 함!
    }
}