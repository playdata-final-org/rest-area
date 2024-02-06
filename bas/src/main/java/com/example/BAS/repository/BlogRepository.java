package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blogs,Long> {
    Blogs findByBlogId(Long blogId);
    @Query("SELECT b.blogId FROM Users u JOIN u.blogs b WHERE u.userId = :userId")
    Long findBlogIdByUserId(@Param("userId") Long userId);

    List<Blogs> findByUsers(Users savedUser);

    List<Blogs> findByCategory(BlogCategory category);

}
