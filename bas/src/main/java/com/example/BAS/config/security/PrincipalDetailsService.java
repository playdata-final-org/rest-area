package com.example.BAS.config.security;

import com.example.BAS.dao.user.UserDAO;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// TODO-2 docs 을 사용할것이라면 official docs format 을 따라 가는게 좋다.
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {
    //PrincipalDetailsService는 UserDetailsService를 구현한 클래스로,
    //사용자 정보를 로드하고 Spring Security에서 요구하는 UserDetails 객체로 변환.
    //UserDAO를 사용하여 DB에서 사용자 정보를 조회
    private final UserDAO userDAO;

    //loadUserByUsername 메서드는 Spring Security에서 인증(authentication) 과정 중에 사용되는데,
    // 사용자가 입력한 로그인 아이디(username)를 기반으로 해당 사용자의 정보를 가져옴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userDAO.findByUsername(username);

        if (users == null) {
            //없으면 null반환
            return null;
        } else {

            return new PrincipalDetails(users);
        }
    }


}

