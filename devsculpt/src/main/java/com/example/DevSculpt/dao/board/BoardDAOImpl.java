package com.example.DevSculpt.dao.board;

import com.example.DevSculpt.entity.BoardEntity;
import com.example.DevSculpt.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardDAOImpl implements BoardDAO {
    private final BoardRepository repository;

    @Override
    public BoardEntity save(BoardEntity board) {
        return repository.save(board);
    }

    @Override
    public BoardEntity findByTitle(String title) {
        return repository.findByTitle(title);
    }

    @Override
    public BoardEntity findById(Long boardId) {
        return repository.findById(boardId).get();
    }

    @Override
    public List<BoardEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<BoardEntity> findByCategory(String category, Pageable pageable) {
        return repository.findByCategory(category, pageable);
    }

    @Override
    public BoardEntity readBoardById(Long BoardId) {
        return repository.findById(BoardId).get();
    }

    @Override
    public Page<BoardEntity> findAllByCategoryOrderByCreationDateDesc(String category, Pageable pageable) {
        return repository.findAllByCategoryOrderByCreationDateDesc(category, pageable);
    }

    @Override
    public void deleteBoard(Long boardId) {
        repository.deleteById(boardId);
    }

    @Override
    public Page<BoardEntity> searchBoard(String category, String keyword, Pageable pageable) {
        return repository.findByCategoryAndNicknameOrTitle(
                category, keyword, pageable);
    }
}
