package com.example.BAS.dao.blogAbout;

import com.example.BAS.entitiy.blog.BlogAbout;

import java.util.List;

public interface BlogAboutDAO {
    BlogAbout saveBlogAbout(BlogAbout blogAbout);
    List<BlogAbout> findByAbout(Long blogId);
}
