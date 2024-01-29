package com.example.BAS.controller.user;

import com.example.BAS.dto.blog.BlogRequestDTO;
import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.service.blog.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BlogRestController {
    private final BlogService blogService;
    @PostMapping("/blog/{blogId}")
    public BlogRequestDTO blogCategory(@PathVariable("blogId") Long blogId,
                                       @RequestBody Map<String, String> requestBody) {
        System.out.println("__________________________________________________");
        System.out.println(blogId);
        System.out.println("__________________________________________________");
        String categoryString = requestBody.get("category");

        BlogCategory blogCategory = null;

        try {
            blogCategory = BlogCategory.valueOf(categoryString);

        } catch (IllegalArgumentException e) {

            System.out.println("Invalid category: " + categoryString);
        }
       BlogRequestDTO dto =  blogService.saveBlog(blogId, blogCategory);
        System.out.println("===============================================================================");
        System.out.println(dto);
        System.out.println("===============================================================================");
//        return blogService.saveBlog(blogId, blogCategory);
        return  null;
    }


}
