package com.example.BAS.dto.collection;

import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.files.CollectionFiles;
import com.example.BAS.entitiy.files.CollectionImages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CollectionRequestDTO {
    private Long collectionId;
    private Blogs blogs;
    private List<CollectionImages> collectionImages;
    private CollectionFiles collectionFiles;
    private String title;
    private String content;
    private String grade;
    private String membershipType;
    private Long tierId;
    private List<String> imageUrls;
    private String fileUrl;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deleteDate;

}
