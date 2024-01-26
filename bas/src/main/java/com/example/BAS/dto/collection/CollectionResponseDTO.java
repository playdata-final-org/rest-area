package com.example.BAS.dto.collection;

import com.example.BAS.entitiy.blog.Blogs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CollectionResponseDTO {
    private Long collectionId;
    private Blogs blogs;
    private List<MultipartFile> collectionImages;
    private MultipartFile collectionFiles;
    private String title;
    private String content;
    private String grade;
    private String membershipType;
    private Long tierId;



}
