package com.example.BAS.dao.auth;

import com.example.BAS.entitiy.authority.Authority;

public interface AuthDAO {

    Authority findByAuthorityName(String authorityName);

    Authority save(Authority authority);
}
