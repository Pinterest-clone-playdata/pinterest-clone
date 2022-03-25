package com.team1.pinterest.Repository;

import com.team1.pinterest.DTO.PinDTO;
import com.team1.pinterest.DTO.PinSearchCondition;
import com.team1.pinterest.Entitiy.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PinRepositoryCustom {

    Slice<PinDTO> findAllPinHome(Pageable pageable, PinSearchCondition pinSearchCondition);

    Slice<PinDTO> findFollowersByUser(Pageable pageable, User user);

    Slice<PinDTO> findMyPins(Pageable pageable, User user, PinSearchCondition condition);
}
