package com.example.DevSculpt.repository;

import com.example.DevSculpt.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByBoardBoardId(Long boardId);

    @Query("SELECT c FROM CommentEntity c JOIN FETCH c.user WHERE c.board.id = :boardId")
    List<CommentEntity> findByBoardBoardIdWithUser(@Param("boardId") Long boardId);
}