package com.example.BAS.controller.user;


import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.dto.membership.MembershipResponseDTO;
import com.example.BAS.dto.membership.MembershipTierRequestDTO;
import com.example.BAS.dto.membership.MembershipTierResponseDTO;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.service.blog.BlogService;
import com.example.BAS.service.boostHistory.BoostHistoryService;
import com.example.BAS.service.membership.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;
    private final BlogService blogService;
    private final BoostHistoryService boostHistoryService;

    @GetMapping("/blogMembershipWrite/{blogId}")
    public String showBlogMembershipWrite(@PathVariable("blogId") Long blogId,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        String rolePage = principalDetails.rolePage();
        Blogs blog = blogService.findByBlogId(blogId);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            return "blog/blog-write-membershipChoose";
        }else{
            return "user/main";
        }
    }
    @PostMapping("/blogMembershipWrite/{blogId}")
    public String saveMembership(@PathVariable("blogId") Long blogId,
                                 @ModelAttribute MembershipTierResponseDTO membershipTierResponseDTO
                               , @ModelAttribute MembershipResponseDTO membershipResponseDTO,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long creatorId = principalDetails.getUsers().getUserId();
        membershipService.saveMembershipTier(creatorId,blogId,membershipTierResponseDTO);
        return "redirect:/blog/" + blogId;
    }
    @GetMapping("/updateBlogMembershipWrite/{blogId}")
    public String showUpdateBlogMembershipWrite(@PathVariable("blogId") Long blogId,
                                          @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        String rolePage = principalDetails.rolePage();
        Blogs blog = blogService.findByBlogId(blogId);
        List<MembershipTierRequestDTO> membershipList = membershipService.getAllMemberships(blogId);
        System.out.println("membershipList = " + membershipList);
        if (rolePage == null) {
            model.addAttribute("profileImageUrl", principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("membershipList",membershipList);
            return "blog/update-blog-write-membershipChoose";
        }else{
            return "user/main";
        }
    }
    @PostMapping("/updateBlogMembershipWrite/{blogId}")
    public String updateMembership(@PathVariable("blogId") Long blogId,
                                 @ModelAttribute MembershipTierResponseDTO membershipTierResponseDTO
                                ,@ModelAttribute MembershipResponseDTO membershipResponseDTO,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long creatorId = principalDetails.getUsers().getUserId();
        membershipService.updateMembershipTier(creatorId,blogId,membershipTierResponseDTO);
        return "redirect:/blog/" + blogId;
    }
    @PostMapping("/blogMembership/{blogId}")
    public String subScribe(@PathVariable("blogId") Long blogId,
                            @RequestParam("selectedTierId") Long selectedTierId,
                            @AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        Blogs blog =blogService.findByBlogId(blogId);

        Long userId = principalDetails.getUsers().getUserId();

        boostHistoryService.save(selectedTierId,userId,blogId);

        return "redirect:/blog/" + blogId;
    }
}
