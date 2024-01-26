package com.example.DevSculpt.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private String origFilename;
    private String fileName;
    private String filePath;
    private LocalDateTime registerDate;
}
