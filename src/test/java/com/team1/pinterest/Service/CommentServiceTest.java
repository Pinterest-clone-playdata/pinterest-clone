package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.CommentDTO;
import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.Role;
import com.team1.pinterest.Entitiy.User;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.io.FileInputStream;
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

    @Test
    @Rollback(value = false)
    void updateComment() throws IOException {

        User user1 = new User("홍길동","111@gmail.com","111");
        em.persist(user1);

        Pin pin1 = new Pin("스타트렉","대머리 아조씨", Role.PUBLIC,user1,"/asdad/asdkalsd.jpg");
        em.persist(pin1);
        User user2 = new User("고길동","111@gmail.com","111");
        em.persist(user2);

        List<CommentDTO> serviceComment = commentService.createComment(user2.getId(), pin1.getId(), "자라나라 머리머리");

        Comment changedComment = new Comment(user2,pin1,"모발 뿜뿜");
        Comment changedComment2 = new Comment(user2,pin1,"머머리머머리");

        List<CommentDTO> commentDTOS = commentService.updateComment(changedComment, user2.getId(), pin1.getId(), serviceComment.get(0).getId());

        // 다른 유저가 접근

        //List<CommentDTO> commentDTOS2 = commentService.updateComment(changedComment2, user1.getId(), pin1.getId(), serviceComment.get(0).getId());
        // 에러 정상 작동 ! java.lang.IllegalArgumentException: 작성자만 comment를 수정할 수 있습니다.
        // Assertions 사용...ㅠ



    }

    @Test
    void readByPinId() throws IOException {
        User user1 = new User("홍길동","111@gmail.com","111");
        em.persist(user1);

        Pin pin1 = new Pin("스타트렉","대머리 아조씨", Role.PUBLIC,user1,"/asdad/asdkalsd.jpg");
        em.persist(pin1);
        User user2 = new User("고길동","111@gmail.com","111");
        em.persist(user2);

        List<CommentDTO> serviceComment1 = commentService.createComment(user1.getId(), pin1.getId(), "자라나라 머리머리1");
        List<CommentDTO> serviceComment2 = commentService.createComment(user2.getId(), pin1.getId(), "자라나라 머리머리2");
        List<CommentDTO> serviceComment3 = commentService.createComment(user1.getId(), pin1.getId(), "자라나라 머리머리3");
        List<CommentDTO> serviceComment4 = commentService.createComment(user2.getId(), pin1.getId(), "자라나라 머리머리4");

        List<CommentDTO> commentDTOS = commentService.readByPinId(pin1.getId());

        for (CommentDTO commentDTO:
            commentDTOS ) {
            System.out.println("commentDTO.getContent() = " + commentDTO.getContent());
        }
        

    }

    @Test
    void readById() throws IOException {
        User user1 = new User("홍길동","111@gmail.com","111");
        em.persist(user1);

        Pin pin1 = new Pin("스타트렉","대머리 아조씨", Role.PUBLIC,user1,"/asdad/asdkalsd.jpg");
        em.persist(pin1);
        User user2 = new User("고길동","111@gmail.com","111");
        em.persist(user2);

        List<CommentDTO> serviceComment1 = commentService.createComment(user1.getId(), pin1.getId(), "자라나라 머리머리1");


        Comment comment  = new Comment(user1,pin1,"모발 뿜뿜");
        em.persist(comment);
        List<CommentDTO> comment1 = commentService.createComment(comment.getUser().getId(), comment.getPin().getId(), comment.getContent());

        List<CommentDTO> result = commentService.readById(comment.getId());

        Assertions.assertThat(comment1.get(0).getContent()).isEqualTo(result.get(0).getContent());
    }

    @Test
    //@Rollback(value = false)
    void deleteComment() throws IOException {
        User user1 = new User("홍길동","111@gmail.com","111");
        em.persist(user1);

        Pin pin1 = new Pin("스타트렉","대머리 아조씨", Role.PUBLIC,user1,"/asdad/asdkalsd.jpg");
        em.persist(pin1);
        User user2 = new User("고길동","111@gmail.com","111");
        em.persist(user2);

        List<CommentDTO> serviceComment1 = commentService.createComment(user2.getId(), pin1.getId(), "자라나라 머리머리1");

        //
        //commentService.deleteComment(user1.getId(),serviceComment1.get(0).getId());
        // 삭제 OK
        // 다른 유저 삭제 못하는 것 확인

        //pin 삭제 했을 때 조회 -> 데이터 베이스는 어떻게 처리?

        MockMultipartFile OverSizeImage = new MockMultipartFile("image",
                "test2.jpg",
                "image/jpg",

                new FileInputStream("/Users/namjh/JavaProjects/TeamProject/test/pinterest-clone/src/test/resources/testImage.jpeg"));

        //Pin pin  = new Pin("TITLE","content",Role.PUBLIC);
        PinForm pinForm = new PinForm("TITLE1", "content", Role.PUBLIC);

        // 현재 CreatePin 수정
//        List<PinDTO> pin = pinService.createPin(pinForm,user1.getId(),OverSizeImage);

        // 추후 테스트 예정




        //List<CommentDTO> commentDTOS = commentService.readById(serviceComment1.get(0).getId());



    }
}