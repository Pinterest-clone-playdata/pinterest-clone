package com.team1.pinterest.Entitiy;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class Follower {

    @Id @GeneratedValue(strategy = IDENTITY)
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
