package com.example.DevSculpt.dao.user;

import com.example.DevSculpt.entity.UserEntity;

public interface UserDAO {
    UserEntity save(UserEntity user);

    UserEntity findById(Long devId);

    UserEntity findByUsernameOrNickName(String username, String nickName);

    UserEntity login(String username);

    boolean idcheck(String username);

    UserEntity findByUser(Long devId);

    void deleteUser(Long devId);
}
