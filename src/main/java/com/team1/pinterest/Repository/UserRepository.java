package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryCustom{
    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
}
