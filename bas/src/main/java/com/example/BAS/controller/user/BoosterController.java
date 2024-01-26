package com.example.BAS.controller.user;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.blog.BlogService;
import com.example.BAS.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);

            return "user/creator-search";
        } else {
            return "user/main";
        }
    }
    @PostMapping("/creator-search")
    public String searchCreators(@RequestParam("nickName") String nickName, Model model) {

        List<Users> foundCreators = usersService.searchCreatorsByNickname(nickName);
        model.addAttribute("foundCreators", foundCreators);
        return "redirect:/creatorSearchList";
    }
    @GetMapping("/creatorSearchList")
    public String showCreatorSearchList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();

        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());

            return "user/creator-search-list";
        } else {
            return "user/main";
        }
    }
}
