package com.example.DevSculpt.service.comment;

import com.example.DevSculpt.dto.comment.CommentRequestDTO;
import com.example.DevSculpt.dto.comment.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO createComment(Long boardId, CommentRequestDTO requestDTO);

    List<CommentResponseDTO> getCommentsByBoardId(Long boardId);
}
