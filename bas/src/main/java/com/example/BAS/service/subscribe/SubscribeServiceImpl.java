package com.example.BAS.service.subscribe;

import com.example.BAS.dao.Membership.CreatorHistoryDAO;
import com.example.BAS.dao.boostHistory.BoostHistoryDAO;
import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.entitiy.blog.BoostDelete;
import com.example.BAS.entitiy.blog.BoostHistory;
import com.example.BAS.entitiy.blog.Membership_tier;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscribeServiceImpl implements SubscribeService {

    private final BoostHistoryDAO boostHistoryDAO;
    private final CreatorHistoryDAO creatorHistoryDAO;
    private final UserDAO userDAO;

    @Override
    public void dailySchedulerService() {
        List<BoostHistory> boostHistories = boostHistoryDAO.findAll();

        for (BoostHistory boostHistory : boostHistories) {
            if (isExpired(boostHistory)) {
                BoostDelete boostDelete = new BoostDelete();
                boostDelete.create(boostHistory);
                boostHistoryDAO.saveHistory(boostDelete);

                boostHistoryDAO.deletedMembership(boostHistory);
            }
        }
    }

    private boolean isExpired(BoostHistory boostHistory) {
        LocalDateTime expirationDate = boostHistory.getExpirationDate();
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expirationDate);
    }


    @Override
    public void monthlySchedulerService() {
        List<BoostHistory> boostHistoryList = boostHistoryDAO.findAll();
        for (BoostHistory boostHistory : boostHistoryList) {
            subtractPointSchedulerService(boostHistory);
        }
    }

    private void increaseOpponentPoint(BoostHistory boostHistory) {

        Users opponentUser = boostHistory.getBlogs().getUsers();

        Membership_tier membershipTier = boostHistory.getMembership_tier();
        int tierPrice = Integer.parseInt(membershipTier.getTierPrice());

        opponentUser.setPoint(opponentUser.getPoint() + tierPrice);

        userDAO.saves(opponentUser);
    }

    private void subtractPointSchedulerService(BoostHistory boostHistory) {
        Users user = boostHistory.getUser();
        Membership_tier membershipTier = boostHistory.getMembership_tier();
        int tierPrice = Integer.parseInt(membershipTier.getTierPrice());
        if (!boostHistory.getIsBoostState()) {
            return;
        }
        if (user.getPoint() >= tierPrice) {
            user.setPoint(user.getPoint() - tierPrice);
            boostHistory.setMembership_tier(membershipTier);
            boostHistory.setUser(user);
            LocalDateTime expirationDate = LocalDateTime.now().plusDays(31);
            boostHistory.setExpirationDate(expirationDate);
            boostHistory.setIsBoostState(true);

            increaseOpponentPoint(boostHistory);

        } else {
            boostHistory.setIsBoostState(false);
        }
        System.out.println("boostHistory = " + boostHistory);
        boostHistoryDAO.save(boostHistory);
        userDAO.save(user);
    }
}