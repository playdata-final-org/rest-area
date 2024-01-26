package com.example.BAS.repository;

import com.example.BAS.entitiy.authority.Authorities;

import com.example.BAS.entitiy.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Authorities,Long> {

}
