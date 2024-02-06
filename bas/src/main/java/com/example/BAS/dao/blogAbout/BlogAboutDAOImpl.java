package com.example.BAS.dao.blogAbout;

import com.example.BAS.entitiy.blog.BlogAbout;
import com.example.BAS.repository.BlogAboutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BlogAboutDAOImpl implements BlogAboutDAO{

    private final BlogAboutRepository blogAboutRepository;
    @Override
    public BlogAbout saveBlogAbout(BlogAbout blogAbout) {
        return blogAboutRepository.save(blogAbout);
    }
    @Override
    public BlogAbout findByAbout(Long blogId) {
        return blogAboutRepository.findAllByBlogs_BlogId(blogId);
    }

    @Override
    public BlogAbout findByBlogs_BlogId(Long blogId) {
        return blogAboutRepository.findByBlogs_BlogId(blogId);
    }

    @Override
    public void save(BlogAbout about) {
        blogAboutRepository.save(about);
    }
}
