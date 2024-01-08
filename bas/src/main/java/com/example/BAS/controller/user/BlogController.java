package com.example.BAS.controller.user;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
@Slf4j
public class BlogController {

    @GetMapping("/blogHome")
    public String showBlogHome() {
        return "blog/blog-home";
    }
    @GetMapping("/blogCollection")
    public String showBlogCollection() {
        return "blog/blog-collection";
    }
    @GetMapping("/blogMembership")
    public String showMembership() {
        return "blog/blog-membership";
    }
    @GetMapping("/blogAbout")
    public String showBlogAbout() {
        return "blog/blog-about";
    }

    @GetMapping("/blogWrite")
    public String showBlogWrite(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if ("ROLE_BOOSTER".equalsIgnoreCase(principalDetails.getUsers().getAuthority().getAuthorityName())) {
            Users booster = principalDetails.getUsers();
            if (booster.getProfileImage() != null) {
                // UUID와 파일 이름을 모델에 추가
                model.addAttribute("profileImageUuid", booster.getProfileImage().getUuid());
                model.addAttribute("profileImageFileName", booster.getProfileImage().getFileName());
            }
            model.addAttribute("booster", booster);
            System.out.println("booster = " + booster);
            return "blog/blog-write";
        } else {
            return "user/main";
        }
    }

    @GetMapping("/blogMembershipWrite")
    public String showBlogMembershipWrite(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if ("ROLE_BOOSTER".equalsIgnoreCase(principalDetails.getUsers().getAuthority().getAuthorityName())) {
            Users booster = principalDetails.getUsers();
            if (booster.getProfileImage() != null) {
                // UUID와 파일 이름을 모델에 추가
                model.addAttribute("profileImageUuid", booster.getProfileImage().getUuid());
                model.addAttribute("profileImageFileName", booster.getProfileImage().getFileName());
            }
            model.addAttribute("booster", booster);
            System.out.println("booster = " + booster);
            return "blog/blog-write-membershipChoose";
        } else {
            return "user/main";
        }
    }

    @GetMapping("/aboutWrite")
    public String showBlogAboutWrite(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if ("ROLE_BOOSTER".equalsIgnoreCase(principalDetails.getUsers().getAuthority().getAuthorityName())) {
            Users booster = principalDetails.getUsers();
            if (booster.getProfileImage() != null) {
                // UUID와 파일 이름을 모델에 추가
                model.addAttribute("profileImageUuid", booster.getProfileImage().getUuid());
                model.addAttribute("profileImageFileName", booster.getProfileImage().getFileName());
            }
            model.addAttribute("booster", booster);
            System.out.println("booster = " + booster);
            return "blog/blog-aboutWrite";
        } else {
            return "user/main";
        }
    }
}