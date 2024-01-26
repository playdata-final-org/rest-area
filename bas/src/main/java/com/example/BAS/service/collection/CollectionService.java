package com.example.BAS.service.collection;

import com.example.BAS.dto.collection.CollectionRequestDTO;
import com.example.BAS.dto.collection.CollectionResponseDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.List;

public interface CollectionService {

    CollectionRequestDTO save(Long blogId, CollectionResponseDTO collectionResponseDTO,String membershipType, Long tierId) throws IOException;
    List<CollectionRequestDTO> getCollectionList(Long blogId);
    ByteArrayResource downloadFiles(String fileUrl) throws IOException;
    List<String> getCollectionImagesUrls(Long collectionId);
    InputStreamResource downloadFile(String fileName) throws IOException;
    String getCollectionFileName(Long collectionId);
    String getCollectionFileUuid(Long collectionId);
}
