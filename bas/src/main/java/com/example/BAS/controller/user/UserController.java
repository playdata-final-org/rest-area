package com.example.BAS.controller.user;

import com.example.BAS.config.security.PrincipalDetails;
import com.example.BAS.dto.charge.ChargeDTO;
import com.example.BAS.service.charge.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final ChargeService chargeService;

    @GetMapping("/chargeChoose/{userId}")
    public String charge(@PathVariable("userId") Long userId
                         ,@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        String rolePage = principalDetails.rolePage();

        if (rolePage == null) {
            model.addAttribute("profileImageUrl",principalDetails.profileImageUrl());
            model.addAttribute("booster", principalDetails.getUsers());
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

}



