package com.example.BAS.controller.api;

import com.example.BAS.dto.booster.SecessionDTO;
import com.example.BAS.entitiy.blog.BoostHistory;
import com.example.BAS.service.boostHistory.BoostHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoostHistoryRestController {
    private final BoostHistoryService boostHistoryService;

    @PostMapping("/secession")
    public void addLike(@RequestBody SecessionDTO secessionDTO) {
        BoostHistory boostHistory = boostHistoryService.findByBoostHistoryId(secessionDTO.getBoostHistoryId());
        boostHistoryService.save(boostHistory);

    }
}
