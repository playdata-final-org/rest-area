package com.example.BAS.dto.charge;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChargeHistoryData {
    private List<Integer> chargePointList ;
    private List<Integer> amountChargedList;
    private List<LocalDateTime> createDates;
}
