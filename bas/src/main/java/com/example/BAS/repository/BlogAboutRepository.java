package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.BlogAbout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogAboutRepository extends JpaRepository<BlogAbout,Long> {
    BlogAbout findAllByBlogs_BlogId(Long blogId);
}
