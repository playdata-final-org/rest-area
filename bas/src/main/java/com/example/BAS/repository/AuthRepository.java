package com.example.BAS.repository;

import com.example.BAS.entitiy.authority.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Authority,Long> {
    Authority findByAuthorityName(String authorityName);

}
