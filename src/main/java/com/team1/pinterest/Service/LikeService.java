package com.team1.pinterest.Service;

import com.team1.pinterest.Entitiy.Image;
import com.team1.pinterest.Entitiy.LikeImage;
import com.team1.pinterest.Entitiy.User;
import com.team1.pinterest.Repository.ImageRepository;
import com.team1.pinterest.Repository.LikeRepository;
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
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public boolean addLike(Long userId, Long imageId){
        User user = userRepository.findById(userId).orElseThrow();
        Image image = imageRepository.findById(imageId).orElseThrow();

        if (isNotAlreadyLike(user, image)){
            likeRepository.save(new LikeImage(user,image));
            image.plusCount();
            return true;
        }

        return false;
    }

    public boolean removeLike(Long userId, Long imageId){
        User user = userRepository.findById(userId).orElseThrow();
        Image image = imageRepository.findById(imageId).orElseThrow();
        Optional<LikeImage> search = likeRepository.findByUserAndImage(user, image);

        if (search.isPresent()){
            likeRepository.delete(search.orElseThrow());
            image.minusCount();
            return true;
        }

        return false;
    }

    private boolean isNotAlreadyLike(User user, Image image) {
        return likeRepository.findByUserAndImage(user,image).isEmpty();
    }
}
