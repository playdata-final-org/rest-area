package com.example.BAS.dao.blogAbout;

import com.example.BAS.entitiy.blog.BlogAbout;

public interface BlogAboutDAO {
    BlogAbout saveBlogAbout(BlogAbout blogAbout);
    BlogAbout findByAbout(Long blogId);

    BlogAbout findByBlogs_BlogId(Long blogId);

    void save(BlogAbout about);
}
