package com.example.BAS.service.file;

import com.example.BAS.dto.file.ImageDTO;

import java.io.IOException;
import java.nio.file.Path;

public interface FileService {
    ImageDTO createImageDTO(String originalSourceName, Path path) throws IOException;



}
