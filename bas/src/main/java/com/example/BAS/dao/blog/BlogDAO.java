package com.example.BAS.dao.blog;

import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;

import java.util.List;

public interface BlogDAO {
    Blogs findByBlogId(Long blogId);
    Blogs save(Blogs createBlog);
    Long findBlogIdByUserId(Long userId);
    List<Blogs> findByUsers(Users savedUser);
    Blogs getByBlogId(Long userId);
}
