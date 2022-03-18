package com.team1.pinterest.Config;

import com.team1.pinterest.Entitiy.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;
    private String path;

    public SessionUser(User user) {
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.path=user.getPath();
    }
}