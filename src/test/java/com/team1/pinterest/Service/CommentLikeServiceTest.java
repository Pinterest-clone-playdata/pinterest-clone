package com.team1.pinterest.Service;

import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import com.team1.pinterest.Entitiy.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Profile("test")
class CommentLikeServiceTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired EntityManager em;
    @Autowired CommentService commentService;
    @Autowired CommentLikeService commentLikeService;

    @Test
    @DisplayName("코멘트 좋아요가 저장이 잘 되는가 또한 코멘트에 카운터 로직이 잘 작동하는지 체크")
    public void CommentCreateSubmitCheck(){
        User user1 = new User("user", "email", "password");
        User user2 = new User("user2", "email2", "password2");
        em.persist(user1);
        em.persist(user2);
        Pin pin1 = new Pin("TITLE1", "content", Role.PUBLIC, user1,"path1");
        em.persist(pin1);
        Comment newContent = new Comment(user1, pin1, "newContent");
        em.persist(newContent);

        boolean commentLike = commentLikeService.createCommentLike(user1.getId(), newContent.getId());

        assertThat(commentLike).isTrue();
        assertThat(newContent.getCount()).isEqualTo(1);
        commentLikeService.deleteCommentLike(user1.getId(), 1L);
        assertThat(newContent.getCount()).isEqualTo(0);
    }
}