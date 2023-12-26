package com.example.BAS.repository;

import com.example.BAS.entitiy.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
