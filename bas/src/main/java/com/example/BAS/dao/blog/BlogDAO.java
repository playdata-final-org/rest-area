package com.example.BAS.dao.blog;

import com.example.BAS.entitiy.blog.BlogCategory;
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

    List<Blogs> findByCategory(BlogCategory category);

    // TODO : 별도 사이드 이펙트 방지를 위해 메소드 생성하였으니 수정 바람.
    Blogs findByUserId(Long userId);

}
