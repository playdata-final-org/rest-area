package com.example.BAS.service.file;

import com.example.BAS.dto.file.ImageDTO;
import com.example.BAS.entitiy.image.ProfileImage;
import com.example.BAS.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    @Override //이미지의 원본 파일 이름과 저장 결로를 받아서 imageDTO에 저장
    public ImageDTO createImageDTO(String originalSourceName, Path path) throws IOException {
        //파일 이름에서 경로를 제외한 부분 추출
        String fileName = originalSourceName.substring(originalSourceName.lastIndexOf("\\")+1);
        //UUID를 이용하여 고유한 식별자 생성
        String uuid = UUID.randomUUID().toString();
        //최종적으로 fileURL구성
        String fileUrl = getDirectory(path) + File.separator + uuid + "_" + fileName;
        //ImageDTO 객체 생성 및 반환
        return ImageDTO.builder()
                .fileName(fileName)
                .uuid(uuid)
                .fileUrl(fileUrl)
                .build();
    }

    private String getDirectory(Path path) throws IOException {
        //해당 경로에 파일이나 디렉토리가 존재하지 않으면 true
        if (!Files.exists(path)) {
            //주어진 경로에 디렉토리를 생성
            Files.createDirectory(path);
        }
        //디렉토리가 존재하던, 새로 생성되었던 상관없이 최종적으로 디렉토리의 경로를 문자열로 반환.
        return path.toString();
    }
}
