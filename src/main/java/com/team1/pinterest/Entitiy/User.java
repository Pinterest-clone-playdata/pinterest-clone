package com.team1.pinterest.Entitiy;


import com.team1.pinterest.Entitiy.Basic.BasicTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends BasicTime {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(length = 30)
    private String username;

    @Column(length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column
    private String path;

    @OneToMany(mappedBy = "user")
    private List<Pin> pins = new ArrayList<>();

    //== 생성 메서드 ==//
    @Builder
    public User(String username, String email, String path, UserRole role) {
        this.username = username;
        this.email = email;
        this.path = path;
        this.role = role;
    }

    public User update(String username, String path) {
        this.username = username;
        this.path = path;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}