package com.team1.pinterest.Entitiy;

//import org.junit.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Profile("test")
@Transactional
class CommentTest {
    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired
    EntityManager em;
    @Test
    public void create (){
        User user1 = new User("user", "email", "password");
        Pin image1 = new Pin("TITLE1", "content", Role.PUBLIC, user1,"dkadlsjdklajslk");
        em.persist(user1); //persist 공부해볼것 !!
        em.persist(image1);
        //public Comment(User user, Pin pin, String content)
        Comment vo1 = new Comment(user1,image1,"초보 어렵다 어렵다 ㅠㅠ ㅋㅋ ");
        em.persist(vo1);

        Assertions.assertThat(user1).isEqualTo(vo1.getUser());
        Assertions.assertThat(image1).isEqualTo(vo1.getPin());
        Assertions.assertThat(vo1.getContent()).isEqualTo("초보 어렵다 어렵다 ㅠㅠ ㅋㅋ ");
    }

}