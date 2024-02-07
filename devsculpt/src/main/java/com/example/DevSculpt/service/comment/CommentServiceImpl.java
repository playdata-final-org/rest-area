package com.example.DevSculpt.service.comment;

import com.example.DevSculpt.dao.alram.AlarmDAO;
import com.example.DevSculpt.dao.board.BoardDAO;
import com.example.DevSculpt.dao.comment.CommentDAO;
import com.example.DevSculpt.dto.comment.CommentRequestDTO;
import com.example.DevSculpt.dto.comment.CommentResponseDTO;
import com.example.DevSculpt.entity.AlarmLogEntity;
import com.example.DevSculpt.entity.BoardEntity;
import com.example.DevSculpt.entity.CommentEntity;
import com.example.DevSculpt.service.alarm.SseEmitterService;
import com.example.DevSculpt.service.security.CustomUserDetail;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentDAO commentDAO;
    private final BoardDAO boardDAO;
    private final AlarmDAO alarmDAO;
    private final SseEmitterService sseEmitterService;

    @Override
    @Transactional
    public CommentResponseDTO createComment(Long boardId, CommentRequestDTO requestDTO) {
        ModelMapper mapper = new ModelMapper();

        BoardEntity board = boardDAO.findById(boardId);

        CustomUserDetail userDetails = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommentEntity newComment = new CommentEntity();
        newComment.setContent(requestDTO.getContent());
        newComment.setBoard(board);
        newComment.setNickName(userDetails.getNickname());
        newComment.setUser(userDetails.getUserResponseDTO().getUserEntity());

        commentDAO.save(newComment);

        CommentResponseDTO commentResponseDTO = mapper.map(newComment, CommentResponseDTO.class);
        commentResponseDTO.setBoardId(board.getBoardId());
        commentResponseDTO.setProfileImage(userDetails.getProfileImage());

        // 게시글 작성자에게 SSE 알림 보내기
        Long postAuthorId = board.getUser().getDevId();
        String notificationMessage = userDetails.getNickname() + "님이 댓글을 남겼습니다.";
        AlarmLogEntity alarmLog = new AlarmLogEntity();
        alarmLog.setMessage(notificationMessage);
        alarmLog.setUser(userDetails.getUserResponseDTO().getUserEntity());
        alarmLog.setComment(newComment);
        alarmDAO.save(alarmLog);
        sseEmitterService.sendNotification(postAuthorId, notificationMessage);

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

    @Override
    public CommentResponseDTO findById(Long commentId) {
        ModelMapper mapper = new ModelMapper();
        CommentEntity commentEntity = commentDAO.getCommentById(commentId);
        CommentResponseDTO commentDTO = mapper.map(commentEntity, CommentResponseDTO.class);
        commentDTO.setBoardId(commentEntity.getBoard().getBoardId());
        commentDTO.setProfileImage(commentEntity.getUser().getProfileImage());
        return commentDTO;
    }
}