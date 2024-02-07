package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.BoostDelete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeleteHistoryRepository extends JpaRepository<BoostDelete,Long> {
    List<BoostDelete> findByUserUserId(Long userId);
}
