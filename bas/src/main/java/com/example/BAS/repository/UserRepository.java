package com.example.BAS.repository;

import com.example.BAS.entitiy.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);

    Users findByEmail(String email);

    @Modifying
    @Query("UPDATE Users u SET u.password = :newPassword WHERE u.email = :email")
    void updatePassword(@Param("email") String email, @Param("newPassword") String newPassword);

    boolean existsByEmail(String email);


    boolean existsByUsername(String username);
}
