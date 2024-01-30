package com.example.BAS.controller.user;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.service.blog.BlogService;
import com.example.BAS.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoosterController {
    private final UsersService usersService;
    private final BlogService blogService;
    @GetMapping("/creator-search")
    public String showCreatorSearch(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        String rolePage = principalDetails.rolePage();
        Long userId = principalDetails.getUsers().getUserId();
        Blogs blog = blogService.getByBlogId(userId);
        List<BlogCategory> categories = Arrays.asList(BlogCategory.values());

        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("categories",categories);

            return "user/creator-search";
        } else {
            return "user/main";
        }
    }
}
