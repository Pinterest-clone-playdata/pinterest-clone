package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PinRepository extends JpaRepository<Pin, Long> {

    boolean existsByTitle(String title);

    Optional<Pin> findByIdAndUser(Long id, User user);
}
