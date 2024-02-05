package com.example.BAS.dao.boostHistory;

import com.example.BAS.entitiy.payment.PointPaymentHistory;

import java.util.List;

public interface PointHistoryDAO {
    void save(PointPaymentHistory paymentHistory);

    List<PointPaymentHistory> findByUserId(Long userId);
}
