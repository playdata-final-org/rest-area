package com.example.BAS.service.membership;

import com.example.BAS.dto.membership.MembershipTierRequestDTO;
import com.example.BAS.dto.membership.MembershipTierResponseDTO;
import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.blog.Memberships;

import java.util.List;

public interface MembershipService {
    List<MembershipTierRequestDTO> saveMembershipTier(Long creatorId,Long blogId,MembershipTierResponseDTO membershipTierResponseDTO);
    List<MembershipTierRequestDTO> getAllMemberships(Long blogId);
    Membership_tier findById(Long selectedTierId);
    Memberships findByMembershipId(Long blogId);
    List<MembershipTierRequestDTO> findTierInfoByMembershipId(Long membershipId);

    Membership_tier findByTierId(Long tierId);
}
