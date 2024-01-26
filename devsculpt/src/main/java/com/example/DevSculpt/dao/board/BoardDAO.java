package com.example.DevSculpt.dao.board;

import com.example.DevSculpt.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardDAO {
    // 게시글 생성
    BoardEntity save(BoardEntity board);

    // 게시글 제목으로 검색
    BoardEntity findByTitle(String title);

    BoardEntity findById(Long boardId);

    List<BoardEntity> findAll();

    // 카테고리별로 게시글 리스트 조회
    Page<BoardEntity> findByCategory(String category, Pageable pageable);

    // 게시글 조회
    BoardEntity readBoardById(Long BoardId);

    // 내림차순으로 정렬
    Page<BoardEntity> findAllByCategoryOrderByCreationDateDesc(String category, Pageable pageable);

    // 게시글 삭제
    void deleteBoard(Long boardId);

    // 게시글 검색
    Page<BoardEntity> searchBoard(String category, String keyword, Pageable pageable);
}
