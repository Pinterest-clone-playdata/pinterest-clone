package com.team1.pinterest.Entitiy;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
public class Follower {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long follower;

    private Long followee;
}
