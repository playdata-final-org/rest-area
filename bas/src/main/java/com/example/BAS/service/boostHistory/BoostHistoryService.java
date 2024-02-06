package com.example.BAS.service.boostHistory;

import com.example.BAS.entitiy.blog.BoostDelete;
import com.example.BAS.entitiy.blog.BoostHistory;

import java.util.List;

public interface BoostHistoryService {
    void save(Long selectedTierId, Long userId,Long blogId);
    List<BoostHistory> findByUserId(Long userId);
    int getBoostersCount(Long blogId);

    BoostHistory findByBoostHistoryId(Long boostHistoryId);

    void save(BoostHistory boostHistory);

    List<BoostHistory> findBlogIdByUserId(Long userId);

    void update(Long selectedTierId, Long userId, Long blogId);

    List<BoostDelete> findByUserIds(Long userId);
}
