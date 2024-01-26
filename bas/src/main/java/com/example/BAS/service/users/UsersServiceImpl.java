package com.example.BAS.service.users;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{

    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void updatePassWord(String username, String password) {
        Users user = userDAO.findByUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userDAO.save(user);
    }
    @Override
    public List<Users> searchCreatorsByNickname(String nickName) {
        return userDAO.findByNickNameContainingIgnoreCase(nickName);
    }
}
