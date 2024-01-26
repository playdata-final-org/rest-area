package com.example.BAS.config.security;


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
    public PrincipalDetails(Users users) {
        this.users = users;

    }
    public void sessionReset(Users updatedUser) {
        this.users = updatedUser;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> users.getCurrentRole());
        return collector;
    }
    public String profileImageUrl() {
        if (users != null && users.getProfileImage() != null) {
            return users.getProfileImage().getFileUrl();
        }
        return null;
    }
    public String rolePage() {
        if (users != null) {
            if ("ROLE_BOOSTER".equalsIgnoreCase(users.getCurrentRole()) ||
                    "ROLE_CREATOR".equalsIgnoreCase(users.getCurrentRole())) {
                return null;
            }
        }
        return "user/main";
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
