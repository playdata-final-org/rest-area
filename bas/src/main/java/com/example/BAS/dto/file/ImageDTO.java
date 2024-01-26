package com.example.BAS.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class ImageDTO {
    private Long ImageId;

    private String uuid;

    private String fileName;

    private String fileUrl;

}
