package com.team1.pinterest.Service;

import com.team1.pinterest.Entitiy.LikeImage;
import com.team1.pinterest.Entitiy.Pin;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Exception.CustomException;
import com.team1.pinterest.Exception.ErrorCode;
import com.team1.pinterest.Repository.LikeRepository;
import com.team1.pinterest.Repository.PinRepository;
import com.team1.pinterest.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PinRepository pinRepository;
    private final UserRepository userRepository;

    public boolean addLike(Long userId, Long imageId){
        User user = findById(userId);
        Pin pin = findByPinId(imageId);

        if (isNotAlreadyLike(user, pin)){
            likeRepository.save(new LikeImage(user,pin));
            pin.plusCount();
            return true;
        } else {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }
    }

    public boolean removeLike(Long userId, Long imageId){
        User user = userRepository.findById(userId).orElseThrow();
        Pin pin = pinRepository.findById(imageId).orElseThrow();
        Optional<LikeImage> search = likeRepository.findByUserAndPin(user, pin);

        if (search.isPresent()){
            likeRepository.delete(search.orElseThrow());
            pin.minusCount();
            return true;
        }
        throw new CustomException(ErrorCode.LIKE_NOT_FOUND);
    }

    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("must have user"));
    }

    private Pin findByPinId(Long pinId) {
        return pinRepository.findById(pinId).orElseThrow(() -> new IllegalArgumentException("not found pin"));
    }

    private boolean isNotAlreadyLike(User user, Pin pin) {
        return likeRepository.findByUserAndPin(user,pin).isEmpty();
    }
}
