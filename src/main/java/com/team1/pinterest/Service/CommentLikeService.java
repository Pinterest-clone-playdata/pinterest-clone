package com.team1.pinterest.Service;

import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.CommentLike;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Exception.CustomException;
import com.team1.pinterest.Exception.ErrorCode;
import com.team1.pinterest.Repository.CommentLikeRepository;
import com.team1.pinterest.Repository.CommentRepository;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.team1.pinterest.Exception.ErrorCode.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public boolean createCommentLike(Long userId, Long commentId){
        User user = findById(userId);
        Comment comment = findByCommentId(commentId);

        if (isNotAlreadyCommentLike(user, comment)) {
            commentLikeRepository.save(new CommentLike(user,comment));
            return true;
        }

        throw new CustomException(DUPLICATE_RESOURCE);
    }

    public boolean deleteCommentLike(Long userId, Long commentLikeId){
        User user = findById(userId);
        CommentLike commentLike = CheckNotRegister(commentLikeId);
        if (commentLike.getUser() != user){
            throw new CustomException(UNAUTHORIZED_COMMENT);
        }
        commentLikeRepository.delete(commentLike);
        return true;
    }

    private CommentLike CheckNotRegister(Long commentLikeId) {
        return commentLikeRepository.findById(commentLikeId).orElseThrow(()-> new IllegalArgumentException(("Is not Register")));
    }

    private CommentLike CheckExistComment(User user, Comment comment) {
        return commentLikeRepository.findByUserAndComment(user, comment).orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
    }


    private boolean isNotAlreadyCommentLike(User user, Comment comment) {
        return commentLikeRepository.findByUserAndComment(user, comment).isEmpty();
    }

    private Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("must have comment"));
    }

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("must have user"));
    }

}
