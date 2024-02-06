package com.example.BAS.service.blog;

import com.example.BAS.dto.About.AboutRequestDTO;
import com.example.BAS.dto.About.AboutResponseDTO;
import com.example.BAS.dto.blog.BlogRequestDTO;
import com.example.BAS.entitiy.blog.BlogAbout;
import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.files.BlogTitleImages;
import com.example.BAS.entitiy.users.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {
    BlogRequestDTO saveBlog(Users user);


    Blogs findByBlogId(Long blogId);
    Long findBlogIdByUserId(Long userId);

    AboutRequestDTO save(Long blogId, AboutResponseDTO aboutResponseDTO);
    BlogAbout findByAbout(Long blogId);

    Blogs getByBlogId(Long userId);


    BlogRequestDTO saveBlog(Long blogId, BlogCategory blogCategory);

    BlogTitleImages uploadImage(MultipartFile file,Long blogId) throws IOException;

    List<Blogs> getBlogsByCategory(BlogCategory category);

    Blogs findByUserId(Long userId);
    void update(Long blogId, AboutResponseDTO aboutResponseDTO);

    BlogAbout findByBlogs_BlogId(Long blogId);
}
