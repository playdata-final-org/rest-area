package com.example.BAS.service.charge;

import com.example.BAS.dto.charge.ChargeDTO;

public interface ChargeService {
   void chargePoint(ChargeDTO chargeDTO, Long userId);
   int findPointByUserId(Long userId);

}
