package com.example.BAS.dao.user;

import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserDAOImpl implements UserDAO {
    private final UserRepository userRepository;
    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public Users save(Users user) {
        return userRepository.save(user);
    }
    @Override
    public Users findByUserId(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByNickName(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Override
    public Users findUserById(Long userId) {
        return userRepository.findUserByUserId(userId);
    }

    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void saveAll(List<Users> users) {
        userRepository.saveAll(users);
    }

    @Override
    public int findPointByUserId(Long userId) {
        return userRepository.findPointByUserId(userId);
    }

    @Override
    public Long getBlogIdByUserId(Long userId) {
        return userRepository.findBlogIdByUserId(userId);
    }

    public List<Users> findByNickNameContainingIgnoreCase(String nickName){
        return userRepository.findByNickNameStartingWith(nickName);
    }
    @Override
    public List<Users> findLikeUser(String userInput) {
        return userRepository.findByNickNameContains(userInput);
    }

    @Override
    public void saves(Users opponentUser) {
        userRepository.save(opponentUser);
    }

}
