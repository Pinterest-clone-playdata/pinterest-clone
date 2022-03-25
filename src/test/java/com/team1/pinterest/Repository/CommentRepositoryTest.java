package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Service.PinService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.awt.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@Transactional
@Profile("test")
@AutoConfigureMockMvc
@SpringBootTest
class CommentRepositoryTest {

    static {
        System.setProperty("spring.config.location", "classpath:/application.yml,classpath:/aws.yml");
    }

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    EntityManager em;
    @Autowired
    PinService pinService;

    @Test
    public void save(){

        User user1 = new User("홍길동","111@naver.com","123123" );
        Pin pin1 = new Pin("호잇","test", Role.PUBLIC,user1,"123123");
        Comment comment = new Comment(user1,pin1,"test");
        //commentRepository.save()
        Comment save = commentRepository.save(comment);
        Comment result = commentRepository.getById(comment.getId());
        Assertions.assertThat(save).isEqualTo(result);

    }

    @Test
    public void read(){
        User user1 = new User("홍길동","111@naver.com","123123" );
        Pin pin1 = new Pin("호잇","test", Role.PUBLIC,user1,"123123");
        Comment comment = new Comment(user1,pin1,"test");
        //commentRepository.save()
        Comment save = commentRepository.save(comment);
        Comment result = commentRepository.getById(comment.getId());

        Assertions.assertThat(result).isEqualTo(save);

    }

    @Test
    public void findByPin(){
        User user1 = new User("홍길동","111@naver.com","123123" );
        User user2 = new User("유관순","112@naver.com","123123" );
        Pin pin1 = new Pin("호잇","test", Role.PUBLIC,user1,"123123");

        em.persist(user1);
        em.persist(user2);
        em.persist(pin1);
        Comment comment1 = new Comment(user1,pin1,"test");
        Comment comment2 = new Comment(user2,pin1,"test2");
        em.persist(comment1);
        em.persist(comment2);
        Comment save1 = commentRepository.save(comment1);
        Comment save2 = commentRepository.save(comment2);
        List<Comment> byPin = commentRepository.findByPin(Optional.of(pin1));
        
        for(Comment comment: byPin){
            System.out.println("comment.getContent() = " + comment.getContent());
        }

    }
    @Test
    public void deleteComment(){
        User user1 = new User("홍길동","111@naver.com","123123" );
        Pin pin1 = new Pin("호잇","test", Role.PUBLIC,user1,"123123");
        Comment comment = new Comment(user1,pin1,"test");

        //commentRepository.save()

        em.persist(user1);
        em.persist(pin1);
        em.persist(comment);

        Comment save = commentRepository.save(comment);
        commentRepository.deleteById(comment.getId());




    }

}