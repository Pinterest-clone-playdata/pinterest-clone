package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.LikeImage;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeImage, Long> {

    Optional<LikeImage> findByUserAndPin(User user, Pin pin);
}
