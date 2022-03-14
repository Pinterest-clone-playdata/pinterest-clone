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

        return CommentToDTO(savedComment);

    }
    //Read -> findByPinId(Long pinId) or Pin ?? -> List<Comment> findByPin(Pin pin);

    //Update -> updateComment(userId,pinId,commentId) original comment -> validation -> update -> commentToDTO

    //Delete -> deleteComment(userId,pinId,commentId) -> validation -> delete (일단 DB delete)

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

    private List<CommentDTO> CommentToDTO(Comment comment){
        List<CommentDTO> list = new ArrayList<>();
        for (Comment attribute : List.of(comment)){
            CommentDTO commentDTO = new CommentDTO(attribute);
            list.add(commentDTO);
        }
        return list;
    }

}
