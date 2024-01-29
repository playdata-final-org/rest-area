package com.example.BAS.dao.blog;

import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlogDAOImpl implements BlogDAO{
    private final BlogRepository blogRepository;
    @Override
    public Blogs findByBlogId(Long blogId) {
        return blogRepository.findByBlogId(blogId);
    }
    @Override
    public Blogs save(Blogs createBlog) {
        return blogRepository.save(createBlog);
    }

    @Override
    public Blogs saves(Blogs blogs) {
        return blogRepository.save(blogs);
    }

    @Override
    public Long findBlogIdByUserId(Long userId) {
        return blogRepository.findBlogIdByUserId(userId);
    }
    @Override
    public List<Blogs> findByUsers(Users savedUser) {
        return blogRepository.findByUsers(savedUser);
    }
    @Override
    public Blogs getByBlogId(Long userId) {
        return blogRepository.findByBlogId(userId);
    }

}
