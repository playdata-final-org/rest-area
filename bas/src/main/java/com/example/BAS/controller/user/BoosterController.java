package com.example.BAS.controller.user;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.entitiy.users.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoosterController {
    @GetMapping("/creator-search")
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
            return "user/creator-search";
        } else {
            return "user/main";
        }
    }

    @GetMapping("/creatorSearchList")
    public String showCreatorSearchList(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        if ("ROLE_BOOSTER".equalsIgnoreCase(principalDetails.getUsers().getAuthority().getAuthorityName())) {
            Users booster = principalDetails.getUsers();
            if (booster.getProfileImage() != null) {
                // UUID와 파일 이름을 모델에 추가
                model.addAttribute("profileImageUuid", booster.getProfileImage().getUuid());
                model.addAttribute("profileImageFileName", booster.getProfileImage().getFileName());
            }
            model.addAttribute("booster", booster);
            System.out.println("booster = " + booster);
            return "user/creator-search-list";
        } else {
            return "user/main";
        }
    }
}
