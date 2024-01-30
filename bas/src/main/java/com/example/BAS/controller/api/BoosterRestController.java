package com.example.BAS.controller.api;

import com.example.BAS.dto.booster.CategoryDataDTO;
import com.example.BAS.entitiy.blog.BlogAbout;
import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.blog.BlogService;
import com.example.BAS.service.boostHistory.BoostHistoryService;
import com.example.BAS.service.collection.CollectionService;
import com.example.BAS.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoosterRestController {
    private final BlogService blogService;
    private final UsersService usersService;
    private final BoostHistoryService boostHistoryService;
    private final CollectionService collectionService;


    @PostMapping("/blogs/category")
    public ResponseEntity<List<CategoryDataDTO>> creatorSearchByCategory(@RequestBody Map<String, String> requestBody) {
        String category = requestBody.get("category");
        List<Blogs> blogs = blogService.getBlogsByCategory(BlogCategory.valueOf(category));

        if (blogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CategoryDataDTO> blogInfoDTOs = new ArrayList<>();

        for (Blogs blog : blogs) {
            Long userId = blog.getUsers().getUserId();
            Users user = usersService.findCreators(userId);
            String creatorImageUrl = user.getProfileImage().getFileUrl();
            String creatorNickName = user.getNickName();
            int boostersCount = boostHistoryService.getBoostersCount(blog.getBlogId());
            int collectionCount = collectionService.getCollectionCount(blog.getBlogId());
            BlogAbout blogAbout = blogService.findByAbout(blog.getBlogId());
            String blogAboutText = "";
            if (blogAbout != null) {
                blogAboutText = blogAbout.getAboutContent();
            }




            CategoryDataDTO blogInfoDTO = new CategoryDataDTO();
            blogInfoDTO.setBlogId(blog.getBlogId());
            blogInfoDTO.setImageUrl(creatorImageUrl);
            blogInfoDTO.setNickName(creatorNickName);
            blogInfoDTO.setBoosterCount(boostersCount);
            blogInfoDTO.setCollectionCount(collectionCount);
            blogInfoDTO.setBlogAbout(blogAboutText);

            blogInfoDTOs.add(blogInfoDTO);
        }

        return ResponseEntity.ok(blogInfoDTOs);
    }
    @PostMapping("/blogs/search")
    public ResponseEntity<List<CategoryDataDTO>> creatorSearchByInput(@RequestBody Map<String, String> requestBody) {
        String userInput = requestBody.get("userInput");
        List<Users> users = usersService.findLikeUser(userInput);


        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CategoryDataDTO> blogInfoDTOs = new ArrayList<>();

        for (Users user : users) {
            Long userId = user.getUserId();
            Users userList = usersService.findCreators(userId);
            Blogs blog = blogService.findByUserId(userId);
            String creatorImageUrl = userList.getProfileImage().getFileUrl();
            String creatorNickName = userList.getNickName();
            int boostersCount = boostHistoryService.getBoostersCount(blog.getBlogId());
            int collectionCount = collectionService.getCollectionCount(blog.getBlogId());
            BlogAbout blogAbout = blogService.findByAbout(blog.getBlogId());
            String blogAboutText = "";
            if (blogAbout != null) {
                blogAboutText = blogAbout.getAboutContent();
            }




            CategoryDataDTO blogInfoDTO = new CategoryDataDTO();
            blogInfoDTO.setBlogId(blog.getBlogId());
            blogInfoDTO.setImageUrl(creatorImageUrl);
            blogInfoDTO.setNickName(creatorNickName);
            blogInfoDTO.setBoosterCount(boostersCount);
            blogInfoDTO.setCollectionCount(collectionCount);
            blogInfoDTO.setBlogAbout(blogAboutText);

            blogInfoDTOs.add(blogInfoDTO);
        }

        return ResponseEntity.ok(blogInfoDTOs);
    }
}