package com.team1.pinterest.Service;

import com.team1.pinterest.Entitiy.Category;
import com.team1.pinterest.Entitiy.Image;
import com.team1.pinterest.Entitiy.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Profile("test")
@SpringBootTest
@Transactional
class LikeServiceTest {

    @Autowired EntityManager em;
    @Autowired LikeService likeService;

    @Test
    void addLike() {
        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        em.persist(user1);
        em.persist(user2);
        Image image1 = new Image("TITLE1", "content", Category.A, user1);
        em.persist(image1);

        likeService.addLike(user2.getId(), image1.getId());

        assertThat(image1.getCount()).isEqualTo(1);
    }

    @Test
    void removeLike() {
        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        em.persist(user1);
        em.persist(user2);
        Image image1 = new Image("TITLE1", "content", Category.A, user1);
        em.persist(image1);

        likeService.addLike(user2.getId(), image1.getId());
        likeService.removeLike(user2.getId(), image1.getId());

        assertThat(image1.getCount()).isEqualTo(0);
    }
}