package com.example.BAS.config.security;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userDAO.findByUsername(username);

        if (users == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
        } else {
            return new PrincipalDetails(users);
        }
    }

}

