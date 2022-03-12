package com.team1.pinterest.Repository;

import com.team1.pinterest.DTO.FollowerDTO;
import com.team1.pinterest.DTO.FollowerSearchCondition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FollowerRepositoryCustom {

    Slice<FollowerDTO> findFollowerWithSlice(Pageable pageable, FollowerSearchCondition condition);
}
