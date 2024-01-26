package com.example.BAS.dao.auth;

import com.example.BAS.entitiy.authority.Authorities;
import com.example.BAS.entitiy.users.Users;

public interface AuthDAO {


    Authorities findByCreatorAuthority(String roleCreator,Long userId);

    Authorities findByBoosterAuthority(String roleBooster,Long userId);




}
