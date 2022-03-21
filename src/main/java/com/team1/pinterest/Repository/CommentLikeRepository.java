package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.CommentLike;
import com.team1.pinterest.Entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}
