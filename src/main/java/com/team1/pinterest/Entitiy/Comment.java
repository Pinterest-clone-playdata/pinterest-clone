package com.team1.pinterest.Entitiy;

import com.team1.pinterest.Entitiy.Basic.BasicTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "Comment_ID")
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

    public Comment(User user, Pin pin, String content) {
        this.user = user;
        this.pin = pin;
        this.content = content;
    }
}
