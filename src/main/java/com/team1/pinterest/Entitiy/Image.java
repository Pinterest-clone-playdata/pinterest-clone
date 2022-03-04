package com.team1.pinterest.Entitiy;

import com.team1.pinterest.Entitiy.Basic.BasicTimeWithCreatedBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class Image extends BasicTimeWithCreatedBy {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String Path;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    private int Count;

    @OneToMany(mappedBy = "image", cascade = CascadeType.ALL)
    Set<LikeImage> likes = new HashSet<>();

    // == 생성 메서드 == //
    public Image(String title, String content, Category category, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
    }

    // == 편의 메서드 == //
    public void plusCount(){
        Count ++;
    }

    public void minusCount(){
        Count --;
    }
}
