package com.example.DevSculpt.dao.comment;

import com.example.DevSculpt.entity.CommentEntity;

import java.util.List;

public interface CommentDAO {
    CommentEntity save(CommentEntity comment);

    List<CommentEntity> findByBoardId(Long boardId);

    List<CommentEntity> findByBoardIdWithUser(Long boardId);
}
