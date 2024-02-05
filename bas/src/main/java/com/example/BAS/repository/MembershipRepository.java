package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.Memberships;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Memberships,Long> {
    Memberships findByMembershipId(Long blogId);

    Memberships findByBlogsBlogId(Long blogId);
}
