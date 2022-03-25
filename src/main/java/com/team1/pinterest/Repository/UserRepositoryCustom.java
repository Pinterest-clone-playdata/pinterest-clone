package com.team1.pinterest.Repository;

import com.team1.pinterest.DTO.UserDTO;
import com.team1.pinterest.DTO.UserSearchCondition;

import java.util.List;

public interface UserRepositoryCustom {

    List<UserDTO> findUsersByUsername(UserSearchCondition condition);
}
