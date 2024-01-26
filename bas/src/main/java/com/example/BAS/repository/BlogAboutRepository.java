package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.BlogAbout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogAboutRepository extends JpaRepository<BlogAbout,Long> {
    List<BlogAbout> findAllByBlogs_BlogId(Long blogId);
}
