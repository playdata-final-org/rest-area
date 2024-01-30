package com.example.BAS.dao.user;

import com.example.BAS.entitiy.users.Users;

import java.util.List;

public interface UserDAO {

    Users findByUsername(String username);
    Users save(Users user);
    Users findByUserId(Long userId);
    boolean existsByUsername(String username);
    boolean existsByNickName(String nickName);
    Users findUserById(Long userId);
    List<Users> findAll();
    void saveAll(List<Users> users);
    int findPointByUserId(Long userId);
    Long getBlogIdByUserId(Long userId);
    List<Users> findByNickNameContainingIgnoreCase(String nickName);

    List<Users> findLikeUser(String userInput);
}
