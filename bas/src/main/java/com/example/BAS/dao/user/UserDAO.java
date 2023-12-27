package com.example.BAS.dao.user;

import com.example.BAS.entitiy.users.Users;

public interface UserDAO {

    Users findByUsername(String username);
    Users save(Users users);
    Users findByUserId(Long userId);
    Users findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
