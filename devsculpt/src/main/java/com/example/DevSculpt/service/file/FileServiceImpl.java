package com.example.DevSculpt.service.file;

import com.example.DevSculpt.dao.board.BoardDAO;
import com.example.DevSculpt.dto.file.FileDTO;
import com.example.DevSculpt.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final BoardDAO boardDAO;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    @Transactional
    public List<FileDTO> saveFiles(List<MultipartFile> files, Long boardId) {
        ModelMapper mapper = new ModelMapper();
        List<FileDTO> fileDTOs = new ArrayList<>();

        // 게시글 ID를 기반으로 파일 식별
        String boardIdString = String.valueOf(boardId);

        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
            } catch (IOException e) {
                throw new RuntimeException("", e);
            }
        }

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                FileEntity fileEntity = new FileEntity();
                // 파일명에 게시글 ID를 사용
                String originalFilename = file.getOriginalFilename();
                String fileName = boardIdString + "_" + StringUtils.cleanPath(originalFilename);
                String filePath = uploadPath + File.separator + fileName;;

                fileEntity.setOrigFilename(originalFilename);
                fileEntity.setFileName(fileName);
                fileEntity.setFilePath(filePath);
                fileEntity.setRegisterDate(LocalDateTime.now());

                try (OutputStream os = new FileOutputStream(filePath)) {
                    os.write(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("", e);
                }

                // 파일 엔터티 정보를 포함한 FileDTO
                FileDTO fileDTO = mapper.map(fileEntity, FileDTO.class);
                fileDTOs.add(fileDTO);
            }
        }
        return fileDTOs;
    }

    @Override
    public String saveProfileImage(MultipartFile profileImage, Long devId) {
        String fileName = devId + "_" + UUID.randomUUID().toString() + "_" + profileImage.getOriginalFilename();
        String filePath = uploadPath + File.separator + fileName;

        try (OutputStream os = new FileOutputStream(filePath)) {
            os.write(profileImage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("", e);
        }

        return fileName;
    }
}
