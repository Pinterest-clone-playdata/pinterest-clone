package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.Follower;
import com.team1.pinterest.Entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowerRepository extends JpaRepository<Follower, Long>, FollowerRepositoryCustom {

    Optional<Follower> findByFollowerAndFollowee(User fromUser, User toUser);

    List<Follower> findByFollower(User fromUser);
}
