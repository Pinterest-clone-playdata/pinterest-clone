package com.team1.pinterest.Service;


import com.team1.pinterest.DTO.CommentDTO;
import com.team1.pinterest.DTO.PinForm;
import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Repository.CommentRepository;
import com.team1.pinterest.Repository.PinRepository;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    // Create -> createComment(id로)  (Comment(User user, Pin pin, String content)) -> commentToDTO
    //dto 로 변환 하지 않으면 어떻게 처리할 것인지도 고민 해보기
    public List<CommentDTO> createComment(Long userId,
                                          Long pinId,
                                          String content) throws IOException{
        User user = findById(userId);
        Pin pin  = findByPinId(pinId);
        Comment comment = new Comment(user,pin,content);
        validation(comment);
        Comment savedComment = commentRepository.save(comment);

        return commentToDTO(savedComment);

    }
    //Read -> findByPinId(Long pinId) or Pin ?? -> List<Comment> findByPin(Pin pin);
    //1. pin -> comment list 전체
    public List<CommentDTO> readByPinId(Long pinId){
        Optional<Pin> pin = pinRepository.findById(pinId);
        List<Comment> commentList = commentRepository.findByPin(pin);
        return commentToDTO(commentList);
    }

    //2. comment id 로 하나의 comment 검색
    public List<CommentDTO> readById(Long id){
        Comment comment = commentRepository.getById(id);
        return commentToDTO(comment);
    }
    //3. 유저 별 comment 검색 필요할까?


    //Update -> updateComment(userId,pinId,commentId) original comment -> validation -> update -> commentToDTO
    public List<CommentDTO> updateComment(Comment comment, Long userId, Long pinId, Long commentId){
        //comment user setter 필요할까?
        User user = findById(userId);
        comment.setUser(user);
        validation(comment);
        Comment originalcomment = findByCommentId(commentId);
        if(originalcomment.getUser() != comment.getUser()){
            throw new IllegalArgumentException("작성자만 comment를 수정할 수 있습니다.");
        }
        if (hasText(comment.getContent())) originalcomment.changeContent(comment.getContent());

        return commentToDTO(originalcomment);
    }
    //Delete -> deleteComment(userId,pinId,commentId) -> validation -> delete (일단 DB delete)

    public void deleteComment(Long userId, Long CommentId){

        User user = findById(userId);
        Comment comment = findByCommentId(CommentId);

        if(comment.getUser() != user){
            throw new IllegalArgumentException("작성자만 Comment를 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
        //Pin이 삭제 됬을 때 코멘트들은 어떻게 처리 될 것인가? -> PinService에서 처리? or 복구 될 수 도 있으니 두기?
    }


    //OAuth가 validation 할 것 -> Mock or 최후 장벽

    // 편의 메서드
    // private Comment findByCommentId(Long Comment) -> 예외 처리 때문?
    // private void validation(Comment comment) 1. pin null(들어가야할덧?or pin service 쓰기?) 2. getUser null 3. comment null(stringUtils.hasText 요거 쓸지)


    private void validation(Comment comment){

        if (comment.getUser() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }

        if (comment.getPin() == null){
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }
        if (comment.getContent() == null) {
            log.warn("Comment cannot be null");
            throw new RuntimeException("Comment cannot be null");
        }
    }

    // User find, pin find는? -> 만들어 쓰기

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("must have user"));
    }
    private Pin findByPinId(Long pinId) {
        return pinRepository.findById(pinId).orElseThrow(() -> new IllegalArgumentException("not found pin"));
    }

    private  Comment findByCommentId(Long commentId){
        return commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("not found comment"));
    }

    private List<CommentDTO> commentToDTO(Comment comment){
        List<CommentDTO> list = new ArrayList<>();
        for (Comment attribute : List.of(comment)){
            CommentDTO commentDTO = new CommentDTO(attribute);
            list.add(commentDTO);
        }
        return list;
    }
    private List<CommentDTO> commentToDTO(List<Comment> commentList){
        List<CommentDTO> list = new ArrayList<>();
        for (Comment attribute : commentList){
            CommentDTO commentDTO = new CommentDTO(attribute);
            list.add(commentDTO);
        }
        return list;
    }

}
