package com.example.BAS.service.blog;

import com.example.BAS.dao.blog.BlogDAO;
import com.example.BAS.dao.blogAbout.BlogAboutDAO;
import com.example.BAS.dto.About.AboutRequestDTO;
import com.example.BAS.dto.About.AboutResponseDTO;
import com.example.BAS.dto.blog.BlogRequestDTO;
import com.example.BAS.entitiy.blog.BlogAbout;
import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogDAO blogDAO;
    private final BlogAboutDAO blogAboutDAO;

    @Override
    public BlogRequestDTO saveBlog(Users user) {
        Blogs createBlog = new Blogs();
        createBlog.setUsers(user);
        Blogs savedBlog = blogDAO.save(createBlog);
        ModelMapper mapper = new ModelMapper();

        return mapper.map(savedBlog, BlogRequestDTO.class);
    }

    @Override
    public BlogRequestDTO saveBlog(Long blogId,BlogCategory blogCategory) {
        Blogs blog = blogDAO.findByBlogId(blogId);
        System.out.println("blogCategory ============================== " + blogCategory);
        System.out.println("blog ========================================= " + blog);
        blog.setCategory(blogCategory);
        Blogs savedBlog = blogDAO.saves(blog);
        ModelMapper mapper = new ModelMapper();

        return mapper.map(savedBlog, BlogRequestDTO.class);
    }

    @Override
    public Blogs findByBlogId(Long blogId) {
        return blogDAO.findByBlogId(blogId) ;
    }

    @Override
    public Long findBlogIdByUserId(Long userId) {
        return blogDAO.findBlogIdByUserId(userId);
    }

    @Override
    public AboutRequestDTO save(Long blogId, AboutResponseDTO aboutResponseDTO) {
        ModelMapper mapper = new ModelMapper();
        BlogAbout blogAbout = mapper.map(aboutResponseDTO, BlogAbout.class);

        Blogs blog = new Blogs();
        blog.setBlogId(blogId);
        blogAbout.setBlogs(blog);

        BlogAbout savedAbout = blogAboutDAO.saveBlogAbout(blogAbout);

        AboutRequestDTO aboutRequestDTO = new AboutRequestDTO();
        blogAbout.setAboutId(savedAbout.getAboutId());
        blogAbout.setBlogs(savedAbout.getBlogs());
        blogAbout.setAboutContent(savedAbout.getAboutContent());
        blogAbout.setAboutTitle(savedAbout.getAboutTitle());
        blogAbout.setCreateDate(savedAbout.getCreateDate());
        blogAbout.setUpdateDate(savedAbout.getUpdateDate());
        blogAbout.setDeleteDate(savedAbout.getDeleteDate());
        return aboutRequestDTO;
    }

    @Override
    public List<BlogAbout> findByAbout(Long blogId) {
        return blogAboutDAO.findByAbout(blogId);
    }

    @Override
    public Blogs getByBlogId(Long userId) {
        return blogDAO.getByBlogId(userId);
    }


}
