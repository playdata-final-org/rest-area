package com.example.BAS.dto.file;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String uuid;
    @NotNull
    private String fileName;
    @NotNull
    private String fileUrl;

}
