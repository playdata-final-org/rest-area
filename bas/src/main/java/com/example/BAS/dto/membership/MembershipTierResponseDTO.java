package com.example.BAS.dto.membership;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembershipTierResponseDTO {
    private String tier1Name;
    private String tier1Content;
    private String tier1Price;
    private String tier1grade;

    private String tier2Name;
    private String tier2Content;
    private String tier2Price;
    private String tier2grade;

    private String tier3Name;
    private String tier3Content;
    private String tier3Price;
    private String tier3grade;

}
