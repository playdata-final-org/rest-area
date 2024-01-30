package com.example.BAS.controller.user;

import com.example.BAS.dto.blog.BlogRequestDTO;
import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.service.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BlogRestController {
    private final BlogService blogService;
    @PostMapping("/blog/{blogId}")
    public BlogRequestDTO blogCategory(@PathVariable("blogId") Long blogId,
                                       @RequestBody Map<String, String> requestBody) {
        String categoryString = requestBody.get("category");

        BlogCategory blogCategory = null;

        try {
            blogCategory = BlogCategory.valueOf(categoryString);

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid category: " + categoryString);
        }
       BlogRequestDTO dto =  blogService.saveBlog(blogId, blogCategory);
//        return blogService.saveBlog(blogId, blogCategory);
        return  null;
    }
    @PostMapping("/blog/{blogId}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable("blogId") Long blogId,
                                         @RequestParam("coverImage") MultipartFile file) throws IOException {

        try {
            blogService.uploadImage(file, blogId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
