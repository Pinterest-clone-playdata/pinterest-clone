package com.team1.pinterest.Repository;

import com.team1.pinterest.Entitiy.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
