package com.example.BAS.dto.booster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoosterDeleteDTO {
    private List<Long> delBlogIds;
    private List<Long> delBoostDeleteIds;
    private List<String> delCreatorNickNames;
    private List<String> delCreatorImgUrls;
    private List<LocalDateTime> delBoostDates;
    private List<LocalDateTime> delExpirationDates;
    private List<String> delTierNames;
    private List<Boolean> delStatus;
}
