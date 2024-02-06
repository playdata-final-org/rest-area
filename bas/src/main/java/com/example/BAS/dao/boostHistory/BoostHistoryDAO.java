package com.example.BAS.dao.boostHistory;

import com.example.BAS.entitiy.blog.BoostDelete;
import com.example.BAS.entitiy.blog.BoostHistory;

import java.util.List;

public interface BoostHistoryDAO {
    void save(BoostHistory boostHistory);

    List<BoostHistory> findAll();


    void deletedMembership(BoostHistory boostHistory);

    List<BoostHistory> findByUserId(Long userId);

    int getBoostersCount(Long blogId);


    BoostHistory findByBoostHistoryId(Long boostHistoryId);

    List<BoostHistory> findBlogIdByUserId(Long userId);

    List<BoostHistory> findByUserUserId(Long userId);

    void saveHistory(BoostDelete boostDelete);

    List<BoostDelete> findByUserIds(Long userId);
}
