package com.team1.pinterest.Service;

import com.team1.pinterest.DTO.FollowerDTO;
import com.team1.pinterest.DTO.FollowerSearchCondition;
import com.team1.pinterest.Entitiy.Follower;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Repository.FollowerRepository;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    public boolean save(Long fromId, Long toId){
        User fromUser = userRepository.findById(fromId).orElseThrow();
        User toUser = userRepository.findById(toId).orElseThrow();

        if (isNotAlreadyFollower(fromUser,toUser)){
            followerRepository.save(new Follower(fromUser,toUser));
            return true;
        }

        return false;
    }

    public boolean remove(Long fromId, Long toId){
        User fromUser = userRepository.findById(fromId).orElseThrow();
        User toUser = userRepository.findById(toId).orElseThrow();
        Optional<Follower> search = followerRepository.findByFollowerAndFollowee(fromUser, toUser);

        if (search.isPresent()){
            followerRepository.delete(search.orElseThrow());
            return true;
        }
        return false;
    }

    public Slice<FollowerDTO> search(Pageable pageable, FollowerSearchCondition condition){
        return followerRepository.findFollowerWithSlice(pageable,condition);

    }

    private boolean isNotAlreadyFollower(User fromUser, User toUser) {
        return followerRepository.findByFollowerAndFollowee(fromUser,toUser).isEmpty();
    }
}
