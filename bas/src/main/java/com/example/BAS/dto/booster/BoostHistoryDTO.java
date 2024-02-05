package com.example.BAS.dto.booster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoostHistoryDTO {
    List<Long> blogIds;
    List<Long> boostHistoryIds;
    List<String> creatorNickNames;
    List<String> creatorImgUrls;
    List<LocalDateTime> boostDates;
    List<LocalDateTime> expirationDates;
    List<String> tierNames;
    List<Boolean> status;

}
