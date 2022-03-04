package com.team1.pinterest.Entitiy;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class LikeImage {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Likes_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "IMAGE_ID")
    private Image image;

    // == 생성 메서드 ==//
    public LikeImage(User user, Image image) {
        this.user = user;
        this.image = image;
    }
//
//    //== 편의 메서드 ==//
//    public void plus(int imageDislike, int imageLike, Image image){
//        this.imageDislike = imageDislike;
//        this.imageLike = imageLike;
//    }
//
//    public void minus(int imageDislike, int imageLike,Image image){
//        this.imageDislike = imageDislike;
//        this.imageLike = imageLike;
//    }
}
