package com.example.DevSculpt.service.board;

import com.example.DevSculpt.dao.board.BoardDAO;
import com.example.DevSculpt.dao.user.UserDAO;
import com.example.DevSculpt.dto.board.BoardRequestDTO;
import com.example.DevSculpt.dto.board.BoardResponseDTO;
import com.example.DevSculpt.dto.file.FileDTO;
import com.example.DevSculpt.dto.user.UserResponseDTO;
import com.example.DevSculpt.entity.BoardEntity;
import com.example.DevSculpt.entity.FileEntity;
import com.example.DevSculpt.entity.UserEntity;
import com.example.DevSculpt.service.file.FileService;
import com.example.DevSculpt.service.security.CustomUserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardDAO boardDAO;
    private final UserDAO userDAO;
    private final FileService fileService;

    @Override
    @Transactional
    public BoardResponseDTO createBoard(BoardRequestDTO requestDTO, List<MultipartFile> files) {
        ModelMapper mapper = new ModelMapper();
        CustomUserDetail customUserDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponseDTO userResponseDTO = customUserDetail.getUserResponseDTO();
        requestDTO.setDevId(userResponseDTO.getDevId());

        // 게시글 엔터티 생성
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(requestDTO.getTitle());
        boardEntity.setCategory(requestDTO.getCategory());
        boardEntity.setContent(requestDTO.getContent());
        boardEntity.setUser(userDAO.findById(requestDTO.getDevId()));

        // 게시글 저장
        BoardEntity savedBoard = boardDAO.save(boardEntity);

        // 파일이 있는 경우에만 파일 업로드 서비스 호출
        if (files != null && !files.isEmpty()) {
            List<FileDTO> fileDTOs = fileService.saveFiles(files, savedBoard.getBoardId());

            // 게시글에 파일 정보 설정
            for (FileDTO fileDTO : fileDTOs) {
                FileEntity fileEntity = mapper.map(fileDTO, FileEntity.class);
                savedBoard.addFile(fileEntity);
            }

            // 게시글 엔터티에 파일 정보가 설정되었으므로 다시 저장
            savedBoard = boardDAO.save(savedBoard);
        }

        // savedBoard를 BoardResponseDTO로 변환
        BoardResponseDTO responseDTO = mapper.map(savedBoard, BoardResponseDTO.class);
        return responseDTO;
    }

    @Override
    public BoardResponseDTO updateBoard(Long boardId, BoardRequestDTO requestDTO) {
        ModelMapper mapper = new ModelMapper();

        // 1. 기존 엔터티를 가져와서 상태 복사
        BoardEntity existingBoard = boardDAO.findById(boardId);

        // 2. 새로운 엔터티 생성하고 변경된 값을 복사
        BoardEntity newBoard = new BoardEntity();
        mapper.map(requestDTO, newBoard);

        // 3. 기존 엔터티의 식별자를 새로운 엔터티로 설정
        newBoard.setBoardId(existingBoard.getBoardId());

        // 4. 기존 엔터티의 사용자 정보를 가져와서 새 엔터티에 설정
        if (existingBoard.getUser() != null) {
            UserEntity newUser = new UserEntity();
            newUser.setDevId(existingBoard.getUser().getDevId());
            newBoard.setUser(newUser);
        }

        // 5. 기존 엔터티의 creationdate를 유지하고 modificationdate를 현재 시간으로 설정
        newBoard.setCreationDate(existingBoard.getCreationDate());
        newBoard.setModificationDate(LocalDateTime.now());

        // 6. 새로운 엔터티 저장
        BoardEntity updatedBoard = boardDAO.save(newBoard);

        // 7. 응답 DTO로 매핑
        BoardResponseDTO responseDTO = mapper.map(updatedBoard, BoardResponseDTO.class);
        return responseDTO;
    }

    @Override
    public List<BoardResponseDTO> getAllBoards() {
        ModelMapper mapper = new ModelMapper();
        List<BoardEntity> allBoards = boardDAO.findAll();
        return allBoards.stream()
                .map(board -> {
                    BoardResponseDTO boardResponseDTO = mapper.map(board, BoardResponseDTO.class);
                    UserEntity userEntity = board.getUser();
                    if (userEntity != null) {
                        boardResponseDTO.setNickName(userEntity.getNickName());
                    }
                    return boardResponseDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<BoardResponseDTO> getBoardsByCategory(String category, Pageable pageable) {
        ModelMapper mapper = new ModelMapper();
        Page<BoardEntity> boardsByCategory = boardDAO.findAllByCategoryOrderByCreationDateDesc(category, pageable);

        return boardsByCategory.map(board -> {
            BoardResponseDTO boardResponseDTO = mapper.map(board, BoardResponseDTO.class);
            UserEntity userEntity = board.getUser();
            if (userEntity != null) {
                boardResponseDTO.setNickName(userEntity.getNickName());
                boardResponseDTO.setProfileImage(userEntity.getProfileImage());
            }
            return boardResponseDTO;
        });
    }

    @Override
    public BoardResponseDTO getBoardById(Long boardId) {
        ModelMapper mapper = new ModelMapper();
        BoardEntity boardEntity = boardDAO.findById(boardId);

        if (boardEntity == null) {
            return null;
        }

        BoardResponseDTO responseDTO = mapper.map(boardEntity, BoardResponseDTO.class);

        // 유저 정보를 가져와서 닉네임을 설정
        UserEntity userEntity = boardEntity.getUser();
        if (userEntity != null) {
            responseDTO.setDevId(userEntity.getDevId());
            responseDTO.setNickName(userEntity.getNickName());
            responseDTO.setProfileImage(userEntity.getProfileImage());
        }
        return responseDTO;
    }

    @Override
    public void deleteBoard(Long boardId) {
        // 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetail) {
            CustomUserDetail currentUser = (CustomUserDetail) authentication.getPrincipal();

            // 게시글 상세 정보 가져오기
            BoardResponseDTO boardDetail = getBoardById(boardId);

            // 현재 사용자의 ID와 게시글 작성자의 ID 비교
            if (currentUser.getUserId().equals(boardDetail.getDevId())) {
                // 현재 사용자와 게시글 작성자가 일치하면 삭제 권한이 있음
                boardDAO.deleteBoard(boardId);
            }
        }
    }

    @Override
    public Page<BoardResponseDTO> searchBoard(String category, String keyword, Pageable pageable) {
        Page<BoardEntity> searchResult = boardDAO.searchBoard(category, keyword, pageable);
        ModelMapper mapper = new ModelMapper();

        return searchResult.map(boardEntity -> {
            BoardResponseDTO boardResponseDTO = mapper.map(boardEntity, BoardResponseDTO.class);

            // 유저 정보 가져오기
            UserEntity userEntity = boardEntity.getUser();
            if (userEntity != null) {
                boardResponseDTO.setNickName(userEntity.getNickName());
                boardResponseDTO.setProfileImage(userEntity.getProfileImage());
            }

            return boardResponseDTO;
        });
    }
}