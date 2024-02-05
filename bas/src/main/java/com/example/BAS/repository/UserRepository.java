package com.example.BAS.repository;

import com.example.BAS.entitiy.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users,Long> {
    Users findByUsername(String username);
    @Modifying
    @Query("UPDATE Users u SET u.password = :newPassword WHERE u.nickName = :nickName")
    void updatePassword(@Param("nickName") String nickName, @Param("newPassword") String newPassword);
    boolean existsByUsername(String username);
    boolean existsByNickName(String nickName);

    Users findUserByUserId(Long userId);
    @Query("SELECT u.point FROM Users u WHERE u.userId = :userId")
    int findPointByUserId(@Param("userId") Long userId);

    @Query("SELECT b.blogId FROM Users u JOIN u.blogs b WHERE u.userId = :userId")
    Long findBlogIdByUserId(@Param("userId") Long userId);

    List<Users> findByNickNameContainingIgnoreCase(String nickName);

    List<Users> findAll();

    List<Users> findByNickNameStartingWith(String nickName);

    List<Users> findByNickNameContains(String userInput);


}
