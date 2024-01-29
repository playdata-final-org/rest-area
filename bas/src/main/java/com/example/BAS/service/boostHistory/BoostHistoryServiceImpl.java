package com.example.BAS.service.boostHistory;

import com.example.BAS.dao.blog.BlogDAO;
import com.example.BAS.dao.boostHistory.BoostHistoryDAO;
import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.blog.BoostHistory;
import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.membership.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoostHistoryServiceImpl implements BoostHistoryService{

        private final BoostHistoryDAO boostHistoryDAO;
        private final UserDAO userDAO;
        private final BlogDAO blogDAO;
        private final MembershipService membershipService;

    @Override
    public void save(Long selectedTierId, Long userId, Long blogId) {

        Users user = userDAO.findByUserId(userId);
        Blogs blogs = blogDAO.findByBlogId(blogId);
        Membership_tier membershipTier = membershipService.findById(selectedTierId);

        int tierPrice = Integer.parseInt(membershipTier.getTierPrice());

        if(user.getPoint() >= tierPrice){
            user.setPoint(user.getPoint()-tierPrice);

            BoostHistory boostHistory = new BoostHistory();
            boostHistory.setMembership_tier(membershipTier);
            boostHistory.setUser(user);
            boostHistory.setBlogs(blogs);
            LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(5);//테스트 5분
//            LocalDateTime expirationDate = LocalDateTime.now().plusDays(31);
            boostHistory.setExpirationDate(expirationDate);
            boostHistory.setIsBoostState(true);
            boostHistoryDAO.save(boostHistory);
        }
        userDAO.save(user);
    }

    @Override
    public List<BoostHistory> findByUserId(Long userId) {
        return boostHistoryDAO.findByUserId(userId);
    }

    @Override
    public int getBoostersCount(Long blogId) {
        return boostHistoryDAO.getBoostersCount(blogId);
    }
}
