package com.example.BAS.service.users;

import com.example.BAS.entitiy.users.Users;

import java.util.List;

public interface UsersService {
    void updatePassWord(String email, String password);
    List<Users> searchCreatorsByNickname(String nickName);
}
