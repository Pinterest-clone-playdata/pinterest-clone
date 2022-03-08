package com.team1.pinterest.Controller;

import com.team1.pinterest.Entitiy.Category;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Service.FollowerService;
import com.team1.pinterest.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.DBInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        private final EntityManager em;
        private final LikeService likeService;
        private final FollowerService followerService;

        public void DBInit() {

            User user1 = new User("user", "email", "password");
            User user2 = new User("user2", "email2", "password2");
            User user3 = new User("user3", "email3", "password3");
            User user4 = new User("user4", "email4", "password4");
            em.persist(user1);
            em.persist(user2);
            em.persist(user3);
            em.persist(user4);
            Pin image1 = new Pin("TITLE1", "content", Category.A, user1);
            em.persist(image1);

            likeService.addLike(user2.getId(), image1.getId());
            followerService.save(user1.getId(),user2.getId());
            followerService.save(user2.getId(),user4.getId());
        }
    }
}
