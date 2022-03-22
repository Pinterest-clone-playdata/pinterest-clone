package com.team1.pinterest.Entitiy;

import com.team1.pinterest.Entitiy.Basic.BasicTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BasicTime {
    //Database comment table
    //Comment_ID key  (Long)
    //User_ID key3 (Long)
    //Image_ID(Pin_ID key2(Long)
    //Content  (varchar2 1000 - 65535)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Pin_ID")
    private Pin pin;
    // varchar2 500자 제한
    @Column(length = 1000, nullable = false)
    private String content;

    @Column(columnDefinition = "int")
    private int count;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    Set<CommentLike> likes = new HashSet<>();


    public Comment(String content) {
        this.content = content;
    }

    public Comment(User user, Pin pin, String content) {
        this.user = user;
        this.pin = pin;
        this.content = content;
    }

    public void plusCount(){
        count ++;
    }

    public void minusCount(){
        count --;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPin(Pin pin) {
        this.pin = pin;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
