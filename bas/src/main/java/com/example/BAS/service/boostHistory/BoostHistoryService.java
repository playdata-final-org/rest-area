package com.example.BAS.service.boostHistory;

import com.example.BAS.entitiy.blog.BoostHistory;

import java.util.List;

public interface BoostHistoryService {
    void save(Long selectedTierId, Long userId,Long blogId);
    List<BoostHistory> findByUserId(Long userId);
    int getBoostersCount(Long blogId);

    BoostHistory findByBoostHistoryId(Long boostHistoryId);

    void save(BoostHistory boostHistory);
}
