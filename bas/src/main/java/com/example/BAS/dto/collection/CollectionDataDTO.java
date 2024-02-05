package com.example.BAS.dto.collection;

import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CollectionDataDTO {
    private String profileImageUrl;
    private Users booster;
    private Long tierId;
    private Blogs blog;
    private Page<CollectionRequestDTO> collections;
    private List<List<String>> collectionImagesUrlsList;
    private List<String> collectionFileNames;
    private List<String> collectionUuids;
    private String category;
    private List<Long> collectionIdList;
    private List<List<CommentDTO>> comments;

}
