package com.team1.pinterest.Entitiy;

import com.team1.pinterest.Entitiy.Basic.BasicTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class Pin extends BasicTime {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(columnDefinition = "int", name = "PIN_ID")
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(length = 50, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(columnDefinition = "int")
    private int count;

    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    Set<LikeImage> likes = new HashSet<>();

    // == 생성 메서드 == //
    public Pin(String title, String content, Role role) {
        this.title = title;
        this.content = content;
        this.role = role;
    }

    public Pin(String title, String content, Role role, User user) {
        this.title = title;
        this.content = content;
        this.role = role;
        this.user = user;
    }

    public Pin(String title, String content, Role role, User user, String path) {
        this.title = title;
        this.content = content;
        this.role = role;
        this.user = user;
        this.path = path;
    }

    // == 편의 메서드 == //
    public void plusCount(){
        count ++;
    }

    public void minusCount(){
        count --;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void changeContent(String content){
        this.content = content;
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeRole(Role role){
        this.role = role;
    }
}
