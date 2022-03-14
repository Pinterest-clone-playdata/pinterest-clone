package com.team1.pinterest.Repository;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinSearchCondition;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Service.FollowerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Profile("test")
class PinRepositoryImplTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired EntityManager em;
    @Autowired PinRepository pinRepository;
    @Autowired FollowerService followerService;

    @Test
    public void findAllPinHomeTest(){
        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        em.persist(user1);
        em.persist(user2);

        Pin pin1 = new Pin("TITLE1", "content", Role.PUBLIC, user1,"tempPath");
        em.persist(pin1);

        Pin pin2 = new Pin("TITLE2", "content2", Role.PUBLIC, user2,"tempPath2");
        em.persist(pin2);

        Pageable pageable = PageRequest.of(0,10);
        PinSearchCondition condition = PinSearchCondition.builder().username("user").build();
        PinSearchCondition condition2 = PinSearchCondition.builder().title("TITLE1").build();
        PinSearchCondition condition3 = PinSearchCondition.builder().content("content").build();

        List<Pin> pins = new ArrayList<>();
        pins.add(pin2);
        pins.add(pin1);

        Slice<PinDTO> allPinHome = pinRepository.findAllPinHome(pageable, condition);
        Slice<PinDTO> allPinHome2 = pinRepository.findAllPinHome(pageable, condition2);
        Slice<PinDTO> allPinHome3 = pinRepository.findAllPinHome(pageable, condition3);

        String username = allPinHome.getContent().get(0).getUsername();
        String title = allPinHome2.getContent().get(0).getTitle();
        String content = allPinHome3.getContent().get(0).getContent();

        assertThat(username).isEqualTo("user");
        assertThat(title).isEqualTo("TITLE1");
        assertThat(content).isEqualTo("content2");
        assertThat(allPinHome3.getContent().get(0).getUsername()).isEqualTo(user2.getUsername());
    }

    @Test
    public void findAllFollower(){
        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        User user3 = new User("user3", "email3", "password3");
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);

        Pin pin1 = new Pin("TITLE1", "content", Role.PUBLIC, user1,"tempPath");
        em.persist(pin1);

        Pin pin2 = new Pin("TITLE2", "content2", Role.PUBLIC, user2,"tempPath2");
        em.persist(pin2);

        Pin pin4 = new Pin("TITLE4", "content4", Role.PUBLIC, user2,"tempPath2");
        em.persist(pin4);

        Pin pin5 = new Pin("TITLE5", "content5", Role.PUBLIC, user3,"tempPath2");
        em.persist(pin5);

        Pin pin3 = new Pin("TITLE3", "content3", Role.PUBLIC, user3,"tempPath3");
        em.persist(pin3);

        followerService.save(user1.getId(),user2.getId());
        followerService.save(user1.getId(),user3.getId());

        Pageable pageable = PageRequest.of(0,10);

        Slice<PinDTO> result = pinRepository.findFollowersByUser(pageable,user1);
        assertThat(result.getContent().size()).isEqualTo(4);
        System.out.println(result.getContent().get(0).getPath());

    }
}