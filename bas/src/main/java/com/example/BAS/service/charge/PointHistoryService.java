package com.example.BAS.service.charge;

import com.example.BAS.entitiy.payment.PointPaymentHistory;

import java.util.List;

public interface PointHistoryService {
    List<PointPaymentHistory> findByUserId(Long userId);
}
