package com.example.BAS.service.blog;

import com.example.BAS.dao.blog.BlogDAO;
import com.example.BAS.dao.blogAbout.BlogAboutDAO;
import com.example.BAS.dto.About.AboutRequestDTO;
import com.example.BAS.dto.About.AboutResponseDTO;
import com.example.BAS.dto.blog.BlogRequestDTO;
import com.example.BAS.dto.file.ImageDTO;
import com.example.BAS.entitiy.blog.BlogAbout;
import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.files.BlogTitleImages;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.file.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogDAO blogDAO;
    private final BlogAboutDAO blogAboutDAO;
    private final FileService fileService;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;
    @Override
    public BlogTitleImages uploadImage(MultipartFile file, Long blogId) throws IOException {
        BlogTitleImages currentImage = blogDAO.getBlogTitleImageByBlogId(blogId);

        try {
            if (!file.getContentType().startsWith("image")) {
                log.warn("이미지 파일이 아닙니다.");
                throw new IllegalArgumentException("이미지 파일이 아닙니다.");
            }

            String originalName = file.getOriginalFilename();
            Path root = Paths.get(uploadPath, "blogs");
            ImageDTO imageDTO = fileService.createImageDTO(originalName, root);

            String relativeFileUrl = "/img/blogs/" + imageDTO.getUuid() + "_" + imageDTO.getFileName();

            Blogs blogs = blogDAO.findByBlogId(blogId);

            BlogTitleImages newImage = BlogTitleImages.builder()
                    .uuid(imageDTO.getUuid())
                    .fileName(imageDTO.getFileName())
                    .fileUrl(relativeFileUrl)
                    .blogs(blogs)
                    .build();

            if (currentImage != null) {
                newImage.setImageId(currentImage.getImageId());
            }

            file.transferTo(Paths.get(imageDTO.getFileUrl()));
            return blogDAO.saved(newImage);
        } catch (IOException e) {
            log.warn("업로드 폴더 생성 실패: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Blogs> getBlogsByCategory(BlogCategory category) {
        return blogDAO.findByCategory(category);
    }

    @Override
    public Blogs findByUserId(Long userId) {
        return blogDAO.findByBlogId(userId);
    }

    private BlogTitleImages defaultProfileImage() throws IOException {
        String defaultProfileImage = "ai.png";
        Path root = Paths.get(uploadPath, "blogs");
        ImageDTO imageDTO = fileService.createImageDTO(defaultProfileImage, root);

        String relativeFileUrl = "/img/blogs/ai.png";

        return BlogTitleImages.builder()
                .uuid(imageDTO.getUuid())
                .fileName(imageDTO.getFileName())
                .fileUrl(relativeFileUrl)
                .build();
    }

    @Override
    public BlogRequestDTO saveBlog(Users user) {
        try {
            Blogs createBlog = new Blogs();
            createBlog.setUsers(user);
            Blogs savedBlog = blogDAO.save(createBlog);

            BlogTitleImages defaultImage = defaultProfileImage();
            defaultImage.setBlogs(savedBlog);
            blogDAO.save(defaultImage);

            ModelMapper mapper = new ModelMapper();
            BlogRequestDTO blogDTO = mapper.map(savedBlog, BlogRequestDTO.class);

            return blogDTO;
        } catch (IOException e) {
            log.error("블로그 저장 중 오류 발생: " + e.getMessage());
            return null;
        }
    }

    @Override
    public BlogRequestDTO saveBlog(Long blogId,BlogCategory blogCategory) {
        Blogs blog = blogDAO.findByBlogId(blogId);
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
    public BlogAbout findByAbout(Long blogId) {
        return blogAboutDAO.findByAbout(blogId);
    }

    @Override
    public Blogs getByBlogId(Long userId) {
        return blogDAO.getByBlogId(userId);
    }
}
