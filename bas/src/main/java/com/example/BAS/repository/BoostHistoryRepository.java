package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.BoostHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoostHistoryRepository extends JpaRepository<BoostHistory,Long> {

    @Query("SELECT bh FROM BoostHistory bh WHERE bh.user.userId = :userId")
    List<BoostHistory> findByUserId(@Param("userId") Long userId);
    @Query("SELECT COUNT(DISTINCT bh.user) FROM BoostHistory bh WHERE bh.blogs.blogId = :blogId")
    int countDistinctUserIdsByBlogId(@Param("blogId") Long blogId);

    BoostHistory findByBoostHistoryId(Long boostHistoryId);
}
