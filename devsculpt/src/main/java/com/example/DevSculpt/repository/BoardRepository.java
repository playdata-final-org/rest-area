package com.example.DevSculpt.repository;

import com.example.DevSculpt.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // 게시글 제목으로 검색
    BoardEntity findByTitle(String title);

    // 카테고리별로 게시글을 페이징해서 가져오기
    Page<BoardEntity> findByCategory(String category, Pageable pageable);

    // 카테고리별로 게시글 생성일자로 내림차순으로 가져오기
    Page<BoardEntity> findAllByCategoryOrderByCreationDateDesc(String category, Pageable pageable);

    // 카테고리별, 닉네임, 제목 검색하기
    @Query("SELECT b FROM BoardEntity b WHERE (b.category = :category OR :category IS NULL) AND (LOWER(b.user.nickName) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(b.title) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<BoardEntity> findByCategoryAndNicknameOrTitle(@Param("category") String category, @Param("search") String search, Pageable pageable);
}
