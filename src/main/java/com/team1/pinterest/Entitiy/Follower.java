package com.team1.pinterest.Entitiy;

import com.team1.pinterest.Entitiy.Basic.BasicTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class Follower extends BasicTime {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "FOLLOWER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "From_ID")
    private User follower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_ID")
    private User followee;

    public Follower(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }
}
