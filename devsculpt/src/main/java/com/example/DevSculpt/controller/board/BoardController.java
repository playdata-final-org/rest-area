package com.example.DevSculpt.controller.board;

import com.example.DevSculpt.dto.board.BoardRequestDTO;
import com.example.DevSculpt.dto.board.BoardResponseDTO;
import com.example.DevSculpt.dto.comment.CommentRequestDTO;
import com.example.DevSculpt.dto.comment.CommentResponseDTO;
import com.example.DevSculpt.service.board.BoardService;
import com.example.DevSculpt.service.comment.CommentService;
import com.example.DevSculpt.service.file.FileService;
import com.example.DevSculpt.service.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dev/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    private final FileService fileService;

    @GetMapping("/lists")
    public String showBoardList(Model model) {
        List<BoardResponseDTO> boardList = boardService.getAllBoards();
        model.addAttribute("boardlist", boardList);
        return "board/community-list";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/create")
    public String createBoardForm(Model model) {
        model.addAttribute("boardRequestDTO", new BoardRequestDTO());
        return "board/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String createBoard(@ModelAttribute BoardRequestDTO requestDTO,
                              @RequestParam(value = "files", required = false) List<MultipartFile> files,
                              Model model) {
        BoardResponseDTO createBoard = boardService.createBoard(requestDTO, files);

        // 파일이 존재하면 처리
        if (files != null && !files.isEmpty()) {
            fileService.saveFiles(files, createBoard.getBoardId());
        }
        model.addAttribute("createBoard", createBoard);
        return "main/index";
    }

    @GetMapping("/posts/{category}/page/{pageNumber}")
    public String categoryPostsByPage(@PathVariable String category,
                                      @PathVariable int pageNumber,
                                      @RequestParam(defaultValue = "8") int size,
                                      @RequestParam(required = false) String search,
                                      Model model) {
        // 페이지 번호를 0부터 시작하지 않고 1부터 시작하도록 수정
        Pageable pageable = PageRequest.of(pageNumber - 1, size); // 페이지 번호 수정

        Page<BoardResponseDTO> posts;
        if (search == null || search.equals("null")) {
            // 검색어가 없는 경우

            posts = boardService.getBoardsByCategory(category, pageable);
        } else {
            // 검색어가 있는 경우
            posts = boardService.searchBoard(category, search, pageable);
        }

        model.addAttribute("postList", posts.getContent());
        model.addAttribute("page", posts);
        model.addAttribute("category", category);
        model.addAttribute("pageNumber", pageNumber); // 수정된 부분
        model.addAttribute("search", search);
        System.out.println(category.toLowerCase());
        return "board/" + category.toLowerCase() + "-list";
    }

    @GetMapping("/{boardId}")
    public String getBoardDetail(@PathVariable Long boardId, Model model) {
        // 게시글 상세 정보 가져오기
        BoardResponseDTO board = boardService.getBoardById(boardId);

        // 게시글의 댓글 목록 가져오기
        List<CommentResponseDTO> comments = commentService.getCommentsByBoardId(boardId);

        // 모델에 게시글 정보와 댓글 목록 추가
        model.addAttribute("board", board);
        model.addAttribute("comments", comments);
        // 상세 페이지로 이동
        return "board/board-detail";
    }

    @GetMapping("/{boardId}/update")
    public String showUpdateForm(@PathVariable Long boardId, Model model) {
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail currentUser = (CustomUserDetail) authentication.getPrincipal();
            // 게시글 상세 정보 가져오기
            BoardResponseDTO boardDetail = boardService.getBoardById(boardId);
            if (currentUser.getUserId().equals(boardDetail.getUserId())) {
                // 모델에 수정할 게시글 정보 추가
                model.addAttribute("board", boardDetail);
                model.addAttribute("boardId", boardId);
                return "board/update-form";
            }
        }
        // 권한이 없는 경우 에러 메시지 반환
        model.addAttribute("errorMessage", "권한이 없습니다.");
        return "error-message"; // 예시로 에러 메시지를 보여줄 페이지로 이동
    }

    @PostMapping("/{boardId}/comment")
    public String createComment(@PathVariable Long boardId, CommentRequestDTO requestDTO, Model model) {
        // 댓글 생성 서비스 호출
        CommentResponseDTO createdComment = commentService.createComment(boardId, requestDTO);

        // 게시글 상세 정보 다시 읽어오기
        BoardResponseDTO board = boardService.getBoardById(boardId);

        // 모델에 게시글 정보와 댓글 목록 추가
        model.addAttribute("board", board);
        model.addAttribute("createdComment", createdComment); // 생성된 댓글 정보도 추가

        // 상세 페이지로 리다이렉트
        return "redirect:/dev/board/{boardId}";
    }

    @PostMapping("/{boardId}/update-board")
    public String processUpdateBoard(@PathVariable Long boardId, @ModelAttribute BoardRequestDTO requestDTO, Model model) {
        // 업데이트 서비스 호출
        BoardResponseDTO updatedBoard = boardService.updateBoard(boardId, requestDTO);

        // 추가: 수정된 게시글 정보를 모델에 추가
        model.addAttribute("board", updatedBoard);

        // 수정된 게시글 상세 페이지로 리다이렉트
        return "redirect:/dev/board/{boardId}";
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> processDeleteBoard(@PathVariable Long boardId, Model model) {
        try {
            boardService.deleteBoard(boardId);
            return ResponseEntity.ok("게시글이 삭제되었습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 혹은 로깅 라이브러리 사용
            model.addAttribute("deleteError", "게시글 삭제에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제에 실패했습니다.");
        }
    }
}