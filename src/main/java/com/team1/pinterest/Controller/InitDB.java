package com.team1.pinterest.Controller;

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


        }
    }
}
