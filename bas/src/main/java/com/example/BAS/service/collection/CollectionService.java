package com.example.BAS.service.collection;

import com.example.BAS.dto.collection.CollectionRequestDTO;
import com.example.BAS.dto.collection.CollectionResponseDTO;
import com.example.BAS.entitiy.blog.Collections;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface CollectionService {
    
    CollectionRequestDTO save(Long blogId, CollectionResponseDTO collectionResponseDTO, String membershipType, Long tierId, String tierName) throws IOException;
    Page<CollectionRequestDTO> getCollectionList(Long blogId, Pageable pageable);
    ByteArrayResource downloadFiles(String fileUrl) throws IOException;
    List<String> getCollectionImagesUrls(Long collectionId);
    InputStreamResource downloadFile(String fileName) throws IOException;
    String getCollectionFileName(Long collectionId);
    String getCollectionFileUuid(Long collectionId);
    int getCollectionCount(Long blogId);

    Collections findByCollectionId(Long collectionId);

    Collections findByCollectionIds(Long collectionId);
}
