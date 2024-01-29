package com.example.BAS.service.membership;

import com.example.BAS.dao.Membership.MembershipDAO;
import com.example.BAS.dto.membership.MembershipTierRequestDTO;
import com.example.BAS.dto.membership.MembershipTierResponseDTO;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.blog.Memberships;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService{
    private final MembershipDAO membershipDAO;
    @Transactional
    @Override
    public List<MembershipTierRequestDTO> saveMembershipTier(Long creatorId,Long blogId,MembershipTierResponseDTO membershipTierResponseDTO) {

        Membership_tier tier1 = new Membership_tier();
        tier1.setTierName(membershipTierResponseDTO.getTier1Name());
        tier1.setTierContent(membershipTierResponseDTO.getTier1Content());
        tier1.setTierPrice(membershipTierResponseDTO.getTier1Price());
        tier1.setGrade("1등급");

        Membership_tier tier2 = new Membership_tier();
        tier2.setTierName(membershipTierResponseDTO.getTier2Name());
        tier2.setTierContent(membershipTierResponseDTO.getTier2Content());
        tier2.setTierPrice(membershipTierResponseDTO.getTier2Price());
        tier2.setGrade("2등급");

        Membership_tier tier3 = new Membership_tier();
        tier3.setTierName(membershipTierResponseDTO.getTier3Name());
        tier3.setTierContent(membershipTierResponseDTO.getTier3Content());
        tier3.setTierPrice(membershipTierResponseDTO.getTier3Price());
        tier3.setGrade("3등급");

        List<Membership_tier> membershipTiers = Arrays.asList(tier1,tier2,tier3);

        Memberships memberships = new Memberships();
        memberships.addMembershipTier(tier1);
        memberships.addMembershipTier(tier2);
        memberships.addMembershipTier(tier3);

        Blogs blog = new Blogs();
        blog.setBlogId(blogId);
        memberships.setBlogs(blog);
        Users user = new Users();
        user.setUserId(creatorId);
        memberships.setCreator(user);

        membershipDAO.saveMemberships(memberships);
        List<Membership_tier> savedMembershipTiers = membershipDAO.saveAll(membershipTiers);

        ModelMapper mapper = new ModelMapper();
        return savedMembershipTiers.stream()
                .map(membership_tier -> mapper.map(membership_tier, MembershipTierRequestDTO.class))
                .collect(Collectors.toList());
    }
    @Override
    public List<MembershipTierRequestDTO> getAllMemberships(Long blogId) {
        List<Membership_tier> membershipTiers = membershipDAO.findByMembershipsBlogsBlogId(blogId);
        return convertToDTOList(membershipTiers);
    }
    @Override
    public Memberships findByMembershipId(Long blogId) {
        return membershipDAO.findByMembershipId(blogId);
    }

    public List<MembershipTierRequestDTO> findTierInfoByMembershipId(Long membershipId) {
        List<Membership_tier> membershipTiers = membershipDAO.findTierInfoByMembershipId(membershipId);
        return membershipTiers.stream()
                .map(MembershipTierRequestDTO::fromMembershipTier)
                .collect(Collectors.toList());
    }

    @Override
    public Membership_tier findByTierId(Long tierId) {
        return membershipDAO.findByTierId(tierId);
    }

    @Override
    public Membership_tier findById(Long selectedTierId) {
        return membershipDAO.findById(selectedTierId);
    }
    private List<MembershipTierRequestDTO> convertToDTOList(List<Membership_tier> membershipTiers) {
        return membershipTiers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private MembershipTierRequestDTO convertToDTO(Membership_tier membershipTier) {
        MembershipTierRequestDTO dto = new MembershipTierRequestDTO();
        dto.setTierId(membershipTier.getTierId());
        dto.setTierName(membershipTier.getTierName());
        dto.setTierContent(membershipTier.getTierContent());
        dto.setTierPrice(membershipTier.getTierPrice());
        dto.setMemberships(Collections.singletonList(membershipTier.getMemberships()));
        dto.setGrade(membershipTier.getGrade());
        return dto;
    }



}
