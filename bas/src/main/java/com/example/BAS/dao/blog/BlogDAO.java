package com.example.BAS.dao.blog;

import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.files.BlogTitleImages;
import com.example.BAS.entitiy.users.Users;

import java.util.List;

public interface BlogDAO {
    Blogs findByBlogId(Long blogId);
    Blogs save(Blogs createBlog);
    BlogTitleImages save(BlogTitleImages blogTitleImages);
    Blogs saves(Blogs blogs);
    Long findBlogIdByUserId(Long userId);
    List<Blogs> findByUsers(Users savedUser);
    Blogs getByBlogId(Long userId);
    BlogTitleImages getBlogTitleImageByBlogId(Long blogId);

    BlogTitleImages saved(BlogTitleImages newImage);
}
