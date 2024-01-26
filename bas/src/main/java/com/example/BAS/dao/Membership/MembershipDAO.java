package com.example.BAS.dao.Membership;

import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.blog.Memberships;

import java.util.List;

public interface MembershipDAO {
    List<Membership_tier> saveAll(List<Membership_tier> tiers);
    void saveMemberships(Memberships memberships);
    List<Membership_tier> findByMembershipsBlogsBlogId(Long blogId);
    Memberships findByMembershipId(Long blogId);
    Membership_tier findById(Long selectedTierId);
    Membership_tier findByTierName(String grade);
    List<Membership_tier> findTierInfoByMembershipId(Long membershipId);
}
