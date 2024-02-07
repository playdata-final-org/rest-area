package com.example.BAS.dao.boostHistory;

import com.example.BAS.entitiy.blog.BoostDelete;
import com.example.BAS.entitiy.blog.BoostHistory;
import com.example.BAS.repository.BoostHistoryRepository;
import com.example.BAS.repository.DeleteHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoostHistoryDAOImpl implements BoostHistoryDAO{
    private final BoostHistoryRepository boostHistoryRepository;
    private final DeleteHistoryRepository deleteHistoryRepository;

    @Override
    public void save(BoostHistory boostHistory) {
        boostHistoryRepository.save(boostHistory);
    }

    @Override
    public List<BoostHistory> findAll() {
        return boostHistoryRepository.findAll();
    }

    @Override
    public void deletedMembership(BoostHistory boostHistory) {
        boostHistoryRepository.delete(boostHistory);
    }

    @Override
    public List<BoostHistory> findByUserId(Long userId) {
        return boostHistoryRepository.findByUserId(userId);
    }

    @Override
    public int getBoostersCount(Long blogId) {
        return boostHistoryRepository.countDistinctUserIdsByBlogId(blogId);
    }

    @Override
    public BoostHistory findByBoostHistoryId(Long boostHistoryId) {
        return boostHistoryRepository.findByBoostHistoryId(boostHistoryId);
    }

    @Override
    public List<BoostHistory> findBlogIdByUserId(Long userId) {
        return boostHistoryRepository.findBlogIdByUserUserId(userId);
    }

    @Override
    public List<BoostHistory> findByUserUserId(Long userId) {
        return boostHistoryRepository.findByUserUserId(userId);
    }

    @Override
    public void saveHistory(BoostDelete boostDelete) {
        deleteHistoryRepository.save(boostDelete);
    }

    @Override
    public List<BoostDelete> findByUserIds(Long userId) {
        return deleteHistoryRepository.findByUserUserId(userId);
    }

}
