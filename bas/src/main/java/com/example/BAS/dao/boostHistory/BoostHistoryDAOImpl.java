package com.example.BAS.dao.boostHistory;

import com.example.BAS.entitiy.blog.BoostHistory;
import com.example.BAS.repository.BoostHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoostHistoryDAOImpl implements BoostHistoryDAO{
    private final BoostHistoryRepository boostHistoryRepository;

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
}
