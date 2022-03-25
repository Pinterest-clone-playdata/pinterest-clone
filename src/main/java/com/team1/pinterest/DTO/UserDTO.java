package com.team1.pinterest.DTO;

import com.querydsl.core.annotations.QueryProjection;
import com.team1.pinterest.Entitiy.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String path;
    private String username;
    private String email;


    //== 생성 메서드 ==//
    @QueryProjection
    public UserDTO(User user) {
        this.path = user.getPath();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

}
