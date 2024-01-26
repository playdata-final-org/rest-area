package com.example.DevSculpt.service.board;

import com.example.DevSculpt.dto.board.BoardRequestDTO;
import com.example.DevSculpt.dto.board.BoardResponseDTO;
import com.example.DevSculpt.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {
    // 게시판 생성
    BoardResponseDTO createBoard(BoardRequestDTO requestDTO, List<MultipartFile> files);

    // 게시판 제목,내용 수정
    BoardResponseDTO updateBoard(Long boardId, BoardRequestDTO requestDTO);

    // 게시글 리스트
    List<BoardResponseDTO> getAllBoards();

    // 카테고리별 게시글 리스트, 페이징처리 추가
    Page<BoardResponseDTO> getBoardsByCategory(String category, Pageable pageable);

    // 게시글 조회
    BoardResponseDTO getBoardById(Long boardId);

    // 게시글 삭제
    void deleteBoard(Long boardId);

    // 게시글 검색
    Page<BoardResponseDTO> searchBoard(String category, String keyword, Pageable pageable);
}
