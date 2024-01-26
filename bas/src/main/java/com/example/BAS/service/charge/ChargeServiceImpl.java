package com.example.BAS.service.charge;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.dto.charge.ChargeDTO;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargeServiceImpl implements ChargeService {

    private final UserDAO userDAO;
    @Override
    public void chargePoint(ChargeDTO chargeDTO, Long userId) {
        Users user = userDAO.findUserById(userId);

        int chargedPoints = chargeDTO.getAmount();
        int newPoints = chargedPoints * 10;

        int currentPoints = user.getPoint();
        int totalPoints = currentPoints + newPoints;

        user.setPoint(totalPoints);
        userDAO.save(user);
    }
    @Override
    public int findPointByUserId(Long userId) {
        return userDAO.findPointByUserId(userId);
    }

}