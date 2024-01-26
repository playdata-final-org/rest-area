package com.example.BAS.dao.auth;

import com.example.BAS.entitiy.authority.Authorities;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthDAOImpl implements AuthDAO{
    private final AuthRepository authRepository;

    @Override
    public Authorities findByCreatorAuthority(String roleCreator,Long userId) {
        Optional<Authorities> authorities = authRepository.findById(userId);
        return authorities.orElse(null);
    }

    @Override
    public Authorities findByBoosterAuthority(String roleBooster,Long userId) {
        Optional<Authorities> authorities = authRepository.findById(userId);
        return authorities.orElse(null);
    }




}
