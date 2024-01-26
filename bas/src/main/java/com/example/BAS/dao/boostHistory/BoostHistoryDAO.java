package com.example.BAS.dao.boostHistory;

import com.example.BAS.entitiy.blog.BoostHistory;

import java.util.List;

public interface BoostHistoryDAO {
    void save(BoostHistory boostHistory);

    List<BoostHistory> findAll();


    void deletedMembership(BoostHistory boostHistory);

    List<BoostHistory> findByUserId(Long userId);
}
