package com.example.BAS.dao.Membership;

import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.blog.Memberships;
import com.example.BAS.repository.MembershipRepository;
import com.example.BAS.repository.MembershipTierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MembershipDAOImpl implements MembershipDAO{
    private final MembershipRepository membershipRepository;
    private final MembershipTierRepository membershipTierRepository;

    @Override
    public List<Membership_tier> saveAll(List<Membership_tier> tiers) {
        return membershipTierRepository.saveAll(tiers);
    }

    @Override
    public void saveMemberships(Memberships memberships) {
       membershipRepository.save(memberships);
    }

    @Override
    public List<Membership_tier> findByMembershipsBlogsBlogId(Long blogId) {
        return membershipTierRepository.findByMembershipsBlogsBlogId(blogId);
    }

    @Override
    public Memberships findByMembershipId(Long blogId) {
        return membershipRepository.findByMembershipId(blogId);
    }

    @Override
    public Membership_tier findById(Long selectedTierId) {
        return membershipTierRepository.findByTierId(selectedTierId);
    }

    @Override
    public Membership_tier findByTierName(String grade) {
        return membershipTierRepository.findByTierName(grade);
    }

    @Override
    public List<Membership_tier> findTierInfoByMembershipId(Long membershipId) {
        return membershipTierRepository.findTierInfoByMembershipId(membershipId);
    }

    @Override
    public Membership_tier findByTierId(Long tierId) {
        return membershipTierRepository.findByTierId(tierId);
    }
}
