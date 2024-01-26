package com.example.DevSculpt.service.file;

import com.example.DevSculpt.dto.file.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<FileDTO> saveFiles(List<MultipartFile> files, Long boardId);

    String saveProfileImage(MultipartFile profileImage, Long devId);
}