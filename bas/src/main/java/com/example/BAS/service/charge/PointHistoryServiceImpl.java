package com.example.BAS.service.charge;

import com.example.BAS.dao.boostHistory.PointHistoryDAO;
import com.example.BAS.entitiy.payment.PointPaymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryServiceImpl implements PointHistoryService{
    private final PointHistoryDAO pointHistoryDAO;

    @Override
    public List<PointPaymentHistory> findByUserId(Long userId) {
        return pointHistoryDAO.findByUserId(userId);
    }
}
