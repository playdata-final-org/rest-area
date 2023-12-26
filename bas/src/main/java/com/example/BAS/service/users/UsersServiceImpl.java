package com.example.BAS.service.users;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.ProfileImageRepository;
import com.example.BAS.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService{
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override //비밀번호 변경
    public void updatePassWord(String email, String password) {
        Users user = userDAO.findByEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userDAO.save(user);
    }


}
