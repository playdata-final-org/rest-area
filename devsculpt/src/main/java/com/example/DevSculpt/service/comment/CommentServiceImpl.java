package com.example.DevSculpt.service.comment;

import com.example.DevSculpt.dao.board.BoardDAO;
import com.example.DevSculpt.dao.comment.CommentDAO;
import com.example.DevSculpt.dto.comment.CommentRequestDTO;
import com.example.DevSculpt.dto.comment.CommentResponseDTO;
import com.example.DevSculpt.entity.BoardEntity;
import com.example.DevSculpt.entity.CommentEntity;
import com.example.DevSculpt.service.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final BoardDAO boardDAO;

    @Override
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO requestDTO) {
        ModelMapper mapper = new ModelMapper();

        // 댓글을 등록할 게시글을 찾기
        BoardEntity board = boardDAO.findById(boardId);

        // 현재 로그인된 사용자 정보 가져오기
        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 새로운 댓글 생성
        CommentEntity newComment = new CommentEntity();
        newComment.setContent(requestDTO.getContent());
        newComment.setBoard(board);
        newComment.setNickName(userDetails.getNickname()); // 댓글 작성자 설정

        // 사용자 정보를 댓글에 설정
        newComment.setUser(userDetails.getUserResponseDTO().getUserEntity());

        commentDAO.save(newComment);

        // CommentResponseDTO 생성 및 반환
        CommentResponseDTO commentResponseDTO = mapper.map(newComment, CommentResponseDTO.class);

        // 게시글 정보 추가
        commentResponseDTO.setBoardId(board.getBoardId());

        // 댓글 작성자의 프로필 이미지 추가
        commentResponseDTO.setProfileImage(userDetails.getProfileImage());

        return commentResponseDTO;
    }

    @Override
    public List<CommentResponseDTO> getCommentsByBoardId(Long boardId) {
        ModelMapper mapper = new ModelMapper();
        List<CommentEntity> comments = commentDAO.findByBoardIdWithUser(boardId);

        List<CommentResponseDTO> commentDTOs = comments.stream()
                .map(comment -> {
                    CommentResponseDTO commentDTO = mapper.map(comment, CommentResponseDTO.class);
                    // 프로필 이미지 설정
                    if (comment.getUser() != null) {
                        commentDTO.setProfileImage(comment.getUser().getProfileImage());
                    }
                    return commentDTO;
                })
                .collect(Collectors.toList());
        return commentDTOs;
    }
}