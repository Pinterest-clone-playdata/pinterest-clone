package com.team1.pinterest.Entitiy;

import com.team1.pinterest.Entitiy.Basic.BasicTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User extends BasicTime {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(length = 30, unique = true)
    private String username;

    @Column(length = 255)
    private String email;

    @Column(length = 200)
    private String password;

    @Column
    private String path;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private final List<Pin> pins = new ArrayList<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE)
    private final List<Follower> followers = new ArrayList<>();

    //== 생성 메서드 ==//
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email, String password, String path) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.path = path;
    }

    //== 편의 메서드 ==//
    public void pathSetting(String path){
        this.path = path;
    }
}
