package com.example.DevSculpt.dao;

import com.example.DevSculpt.entity.UserEntity;

public interface UserDAO {
    UserEntity save(UserEntity user);

    UserEntity findById(Long userId);

    UserEntity findByUsernameOrNickName(String username, String nickName);

    UserEntity login(String username);

    boolean idcheck(String username);

}
