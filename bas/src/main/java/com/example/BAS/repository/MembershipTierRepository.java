package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.Membership_tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembershipTierRepository extends JpaRepository<Membership_tier,Long> {
    List<Membership_tier> findByMembershipsBlogsBlogId(Long blogId);

    Membership_tier findByTierId(Long selectedTierId);

    Membership_tier findByTierName(String grade);
    @Query("SELECT mt FROM Membership_tier mt WHERE mt.memberships.membershipId = :membershipId")
    List<Membership_tier> findTierInfoByMembershipId(@Param("membershipId") Long membershipId);
}
