package com.example.BAS.config.security;

import com.example.BAS.entitiy.authority.Authority;
import com.example.BAS.entitiy.users.Users;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;

@Data
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {
    private Users users;
    private Authority authority;


    public PrincipalDetails(Users users) {
        this.users = users;
        this.authority = users.getAuthority();
    }

    @Override//권한 정보 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Authority의 getAuthorityName()을 통해 권한을 가져와 GrantedAuthority(권한정보)형태로 반환
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> authority.getAuthorityName());
        return collector;
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
