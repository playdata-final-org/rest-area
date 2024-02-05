package com.example.BAS.dao.boostHistory;

import com.example.BAS.entitiy.payment.PointPaymentHistory;
import com.example.BAS.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PointHistoryDAOImpl implements PointHistoryDAO{
    private final PointHistoryRepository pointHistoryRepository;

    @Override
    public void save(PointPaymentHistory paymentHistory) {
        pointHistoryRepository.save(paymentHistory);
    }

    @Override
    public List<PointPaymentHistory> findByUserId(Long userId) {
        return pointHistoryRepository.findByUserId(userId);
    }
}
