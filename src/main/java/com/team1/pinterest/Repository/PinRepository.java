package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {

}
