package com.example.DevSculpt.dao.comment;

import com.example.DevSculpt.entity.CommentEntity;
import com.example.DevSculpt.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentDAOImpl implements CommentDAO {
    private final CommentRepository repository;

    @Override
    public CommentEntity save(CommentEntity comment) {
        return repository.save(comment);
    }

    @Override
    public List<CommentEntity> findByBoardId(Long boardId) {
        return repository.findByBoardBoardId(boardId);
    }

    @Override
    public List<CommentEntity> findByBoardIdWithUser(Long boardId) {
        return repository.findByBoardBoardIdWithUser(boardId);
    }

    @Override
    public CommentEntity getCommentById(Long commentId) {
        return repository.findById(commentId).orElse(null);
    }
}
