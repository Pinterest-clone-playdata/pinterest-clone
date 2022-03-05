package com.team1.pinterest.Service;

import com.team1.pinterest.Entitiy.Category;
import com.team1.pinterest.Entitiy.Image;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Profile("test")
class FollowerServiceTest {

    @Autowired EntityManager em;
    @Autowired FollowerService followerService;
    @Autowired UserRepository userRepository;

    @BeforeEach
    public void before(){
        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        em.persist(user1);
        em.persist(user2);
        Image image1 = new Image("TITLE1", "content", Category.A, user1);
        em.persist(image1);
    }

    @Test
    public void FollowerTest(){
        User user1 = new User("user3", "email", "password");
        User user2 = new User("user4", "email2", "password2");
        em.persist(user1);
        em.persist(user2);

        boolean save = followerService.save(user1.getId(), user2.getId());

        assertThat(save).isTrue();
    }

    @Test
    public void UnFollowerTest(){
        User user1 = new User("user3", "email", "password");
        User user2 = new User("user4", "email2", "password2");
        em.persist(user1);
        em.persist(user2);

        followerService.save(user1.getId(), user2.getId());
        boolean remove = followerService.remove(user1.getId(), user2.getId());

        assertThat(remove).isTrue();
    }
}