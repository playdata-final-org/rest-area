package com.example.BAS.service.file;

import com.example.BAS.dto.file.ImageDTO;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService{
    @Override
    public ImageDTO createImageDTO(String originalSourceName, Path path) throws IOException {
        String fileName = originalSourceName.substring(originalSourceName.lastIndexOf("\\")+1);
        String uuid = UUID.randomUUID().toString();
        String fileUrl = getDirectory(path) + File.separator + uuid + "_" + fileName;
        return ImageDTO.builder()
                .fileName(fileName)
                .uuid(uuid)
                .fileUrl(fileUrl)
                .build();
    }


    private String getDirectory(Path path) throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        return path.toString();
    }
}
