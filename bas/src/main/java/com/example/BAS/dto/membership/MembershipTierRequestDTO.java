package com.example.BAS.dto.membership;

import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.blog.Memberships;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MembershipTierRequestDTO {

    private Long tierId;
    private String tierName;
    private String tierContent;
    private String tierPrice;
    private String grade;

    private List<Memberships> memberships;
    public static MembershipTierRequestDTO fromMembershipTier(Membership_tier membershipTier) {
        MembershipTierRequestDTO dto = new MembershipTierRequestDTO();
        dto.setTierId(membershipTier.getTierId());
        dto.setTierName(membershipTier.getTierName());
        dto.setTierContent(membershipTier.getTierContent());
        dto.setTierPrice(membershipTier.getTierPrice());
        dto.setGrade(membershipTier.getGrade());
        return dto;
    }

}
