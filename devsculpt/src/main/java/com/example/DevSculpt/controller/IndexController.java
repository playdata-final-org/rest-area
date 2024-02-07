package com.example.DevSculpt.controller;

import com.example.DevSculpt.dto.board.BoardResponseDTO;
import com.example.DevSculpt.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dev")
@RequiredArgsConstructor
public class IndexController {
    private final BoardService service;

    @GetMapping("/index")
    public String index(Model model) {
        // 여기에서 각 카테고리별 페이지네이션된 게시글 리스트를 가져와 모델에 추가
        Page<BoardResponseDTO> qaList = service.getBoardsByCategory("Q&A", PageRequest.of(0, 5));
        Page<BoardResponseDTO> communityList = service.getBoardsByCategory("community", PageRequest.of(0, 5));
        Page<BoardResponseDTO> knowledgeList = service.getBoardsByCategory("knowledge", PageRequest.of(0, 5));

        model.addAttribute("qaList", qaList.getContent());
        model.addAttribute("communityList", communityList.getContent());
        model.addAttribute("knowledgeList", knowledgeList.getContent());

        return "main/index";
    }

}
