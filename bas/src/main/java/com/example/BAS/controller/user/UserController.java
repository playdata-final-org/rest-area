package com.example.BAS.controller.user;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.dto.booster.BoostHistoryDTO;
import com.example.BAS.dto.charge.ChargeDTO;
import com.example.BAS.dto.charge.ChargeHistoryData;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.blog.BoostHistory;
import com.example.BAS.entitiy.payment.PointPaymentHistory;
import com.example.BAS.service.blog.BlogService;
import com.example.BAS.service.boostHistory.BoostHistoryService;
import com.example.BAS.service.charge.ChargeService;
import com.example.BAS.service.charge.PointHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ChargeService chargeService;
    private final BlogService blogService;
    private final BoostHistoryService boostHistoryService;
    private final PointHistoryService pointHistoryService;

    @GetMapping("/chargeChoose/{userId}")
    public String charge(@PathVariable("userId") Long userId
                         ,@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();
        Long blogId =blogService.findBlogIdByUserId(userId);
        Blogs blog = blogService.findByBlogId(blogId);

        if (rolePage == null) {
            model.addAttribute("profileImageUrl",principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            return "payment/chargeChoose";
        } else {
            return "user/main";
        }
    }
    @GetMapping("/point")
    public @ResponseBody ResponseEntity<String> chargePoint(@RequestParam("amount") int amount,
                                                            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        Long userId = principalDetails.getUsers().getUserId();
        System.out.println("userId = " + userId);
        chargeService.chargePoint(new ChargeDTO(amount), userId);

        return ResponseEntity.ok("Charge successful");

    }

    @GetMapping("/getUserPoint")
    public @ResponseBody int getPoint(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userId = principalDetails.getUsers().getUserId();
        return chargeService.findPointByUserId(userId);
    }

    @GetMapping("/boostedHistory/{userId}")
    public String showBoostHistory(@PathVariable("userId") Long userId
                        ,@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();
        System.out.println("userId ========================== " + userId);
        Long blogId =blogService.findBlogIdByUserId(userId);
        Blogs blog = blogService.findByBlogId(blogId);

        if (rolePage == null) {
            model.addAttribute("profileImageUrl",principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            return "user/boostedHistory";
        } else {
            return "user/main";
        }
    }
    @GetMapping("/pointHistory/{userId}")
    public String showPointHistory(@PathVariable("userId") Long userId
            ,@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();
        Blogs blog =blogService.findByUserId(userId);
        List<PointPaymentHistory> pointPaymentHistories = pointHistoryService.findByUserId(userId);

        List<Integer> chargePointList = new ArrayList<>();
        List<Integer> amountChargedList = new ArrayList<>();
        List<LocalDateTime> createDates = new ArrayList<>();


        for(PointPaymentHistory pointPaymentHistory : pointPaymentHistories){

            int chargedPoints = pointPaymentHistory.getChargedPoints();
            chargePointList.add(chargedPoints);

            int amountCharged = pointPaymentHistory.getAmountCharged();
            amountChargedList.add(amountCharged);

            LocalDateTime createDate = pointPaymentHistory.getCreateDate();
            createDates.add(createDate);
        }
        ChargeHistoryData chargeHistoryData = new ChargeHistoryData();
        chargeHistoryData.setAmountChargedList(amountChargedList);
        chargeHistoryData.setChargePointList(chargePointList);
        chargeHistoryData.setCreateDates(createDates);


        if (rolePage == null) {
            model.addAttribute("profileImageUrl",principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("chargeHistoryData",chargeHistoryData);
            System.out.println("chargeHistoryData ======================= " + chargeHistoryData);
            return "user/pointHistory";
        } else {
            return "user/main";
        }
    }
    @GetMapping("/boostHistory/{userId}")
    public String showBoostedHistory(@PathVariable("userId") Long userId
                                    ,@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();
        Blogs blog =blogService.findByUserId(userId);
        List<BoostHistory> boostHistory = boostHistoryService.findByUserId(userId);

        List<Long> blogIds = new ArrayList<>();
        List<Long> boostHistoryIds = new ArrayList<>();
        List<String> creatorNickNames = new ArrayList<>();
        List<String> creatorImgUrls = new ArrayList<>();
        List<LocalDateTime> boostDates = new ArrayList<>();
        List<LocalDateTime> expirationDates = new ArrayList<>();
        List<String> tierNames = new ArrayList<>();
        List<Boolean> status = new ArrayList<>();
        for(BoostHistory boostHistory1 : boostHistory){
            Long blogId = boostHistory1.getBlogs().getBlogId();
            blogIds.add(blogId);

            boolean state =boostHistory1.getIsBoostState();
            status.add(state);

            Long boostHistoryId = boostHistory1.getBoostHistoryId();
            boostHistoryIds.add(boostHistoryId);

            String creatorNickName = boostHistory1.getBlogs().getUsers().getNickName();
            creatorNickNames.add(creatorNickName);

            String creatorImgUrl = boostHistory1.getBlogs().getUsers().getProfileImage().getFileUrl();
            creatorImgUrls.add(creatorImgUrl);

            LocalDateTime boostDate =boostHistory1.getBoostDate();
            boostDates.add(boostDate);

            LocalDateTime expirationDate = boostHistory1.getExpirationDate();
            expirationDates.add(expirationDate);

            String tierName = boostHistory1.getMembership_tier().getTierName();
            tierNames.add(tierName);

//            BoostDelete boostDelete = (BoostDelete) boostHistory1.getBoostDeletes();
//            BoostHistory boostHistory2=boostDelete.getBoostHistory();
//            String deleteNickName = boostHistory2.getBlogs().getUsers().getNickName();
        }
        BoostHistoryDTO boostHistoryDTO = new BoostHistoryDTO();
        boostHistoryDTO.setBlogIds(blogIds);
        boostHistoryDTO.setBoostHistoryIds(boostHistoryIds);
        boostHistoryDTO.setCreatorNickNames(creatorNickNames);
        boostHistoryDTO.setCreatorImgUrls(creatorImgUrls);
        boostHistoryDTO.setBoostDates(boostDates);
        boostHistoryDTO.setExpirationDates(expirationDates);
        boostHistoryDTO.setTierNames(tierNames);
        boostHistoryDTO.setStatus(status);


        System.out.println("boostHistoryDTO ============================================= " + boostHistoryDTO);

        if (rolePage == null) {
            model.addAttribute("profileImageUrl",principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
            model.addAttribute("blog",blog);
            model.addAttribute("boostHistoryDTO",boostHistoryDTO);
            return "user/boostHistory";
        } else {
            return "user/main";
        }
    }
//    @GetMapping("/boostDetailPage/{userId}")
//    public String showBoostDetailPage(@PathVariable("userId") Long userId
//            ,@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
//        String rolePage = principalDetails.rolePage();
//        Blogs blog =blogService.findByUserId(userId);
//        List<BoostHistory> boostHistory = boostHistoryService.findByUserId(userId);
//
//        List<Long> blogIds = new ArrayList<>();
//        List<Long> boostHistoryIds = new ArrayList<>();
//        List<String> creatorNickNames = new ArrayList<>();
//        List<String> creatorImgUrls = new ArrayList<>();
//        List<LocalDateTime> boostDates = new ArrayList<>();
//        List<LocalDateTime> expirationDates = new ArrayList<>();
//        List<String> tierNames = new ArrayList<>();
//        List<Boolean> status = new ArrayList<>();
//        for(BoostHistory boostHistory1 : boostHistory){
//            Long blogId = boostHistory1.getUser().getUserId();
//            blogIds.add(blogId);
//
//            boolean state =boostHistory1.getIsBoostState();
//            status.add(state);
//
//            Long boostHistoryId = boostHistory1.getBoostHistoryId();
//            boostHistoryIds.add(boostHistoryId);
//
//            String creatorNickName = boostHistory1.getBlogs().getUsers().getNickName();
//            creatorNickNames.add(creatorNickName);
//
//            String creatorImgUrl = boostHistory1.getBlogs().getUsers().getProfileImage().getFileUrl();
//            creatorImgUrls.add(creatorImgUrl);
//
//            LocalDateTime boostDate =boostHistory1.getBoostDate();
//            boostDates.add(boostDate);
//
//            LocalDateTime expirationDate = boostHistory1.getExpirationDate();
//            expirationDates.add(expirationDate);
//
//            String tierName = boostHistory1.getMembership_tier().getTierName();
//            tierNames.add(tierName);
//
////            BoostDelete boostDelete = (BoostDelete) boostHistory1.getBoostDeletes();
////            BoostHistory boostHistory2=boostDelete.getBoostHistory();
////            String deleteNickName = boostHistory2.getBlogs().getUsers().getNickName();
//        }
//        BoostHistoryDTO boostHistoryDTO = new BoostHistoryDTO();
//        boostHistoryDTO.setBlogIds(blogIds);
//        boostHistoryDTO.setBoostHistoryIds(boostHistoryIds);
//        boostHistoryDTO.setCreatorNickNames(creatorNickNames);
//        boostHistoryDTO.setCreatorImgUrls(creatorImgUrls);
//        boostHistoryDTO.setBoostDates(boostDates);
//        boostHistoryDTO.setExpirationDates(expirationDates);
//        boostHistoryDTO.setTierNames(tierNames);
//        boostHistoryDTO.setStatus(status);
//
//
//        System.out.println("boostHistoryDTO ============================================= " + boostHistoryDTO);
//
//        if (rolePage == null) {
//            model.addAttribute("profileImageUrl",principalDetails.profileImageUrl());
//            model.addAttribute("booster", principalDetails.getUsers());
//            model.addAttribute("blog",blog);
//            model.addAttribute("boostHistoryDTO",boostHistoryDTO);
//            return "user/boostDetailPage";
//        } else {
//            return "user/main";
//        }
//    }
}



