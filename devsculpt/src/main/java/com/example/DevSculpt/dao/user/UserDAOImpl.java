package com.example.DevSculpt.dao.user;

import com.example.DevSculpt.entity.UserEntity;
import com.example.DevSculpt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository repository;

    @Override
    public UserEntity save(UserEntity user) {
        return repository.save(user);
    }

    @Override
    public UserEntity findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public UserEntity findByUsernameOrNickName(String username, String nickName) {
        return repository.findByUsernameOrNickName(username, nickName);
    }

    @Override
    public UserEntity login(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean idcheck(String username) {
        boolean exists = repository.existsByUsername(username);
        return exists;
    }

    @Override
    public UserEntity findByUser(Long devId) {
        return repository.findById(devId).orElse(null);
    }

    @Override
    public void deleteUser(Long devId) {
        repository.deleteById(devId);
    }
}
