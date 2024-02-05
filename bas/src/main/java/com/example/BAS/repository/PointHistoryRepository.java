package com.example.BAS.repository;

import com.example.BAS.entitiy.payment.PointPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointPaymentHistory,Long> {
    @Query("SELECT bh FROM PointPaymentHistory bh WHERE bh.user.userId = :userId")
    List<PointPaymentHistory> findByUserId(@Param("userId") Long userId);
}
