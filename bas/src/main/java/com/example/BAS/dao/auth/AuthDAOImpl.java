package com.example.BAS.dao.auth;

import com.example.BAS.entitiy.authority.Authority;
import com.example.BAS.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthDAOImpl implements AuthDAO{
    private final AuthRepository authRepository;
    @Override
    public Authority findByAuthorityName(String authorityName) {
        return authRepository.findByAuthorityName(authorityName);
    }

    @Override
    public Authority save(Authority authority) {
        return authRepository.save(authority);
    }
}
