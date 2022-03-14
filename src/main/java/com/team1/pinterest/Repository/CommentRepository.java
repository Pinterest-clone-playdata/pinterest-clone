package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.Comment;
import com.team1.pinterest.Entitiy.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPin(Optional<Pin> pin);
}
