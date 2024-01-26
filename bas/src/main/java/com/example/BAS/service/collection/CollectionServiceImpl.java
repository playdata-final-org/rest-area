package com.example.BAS.service.collection;

import com.example.BAS.dao.collection.CollectionDAO;
import com.example.BAS.dto.collection.CollectionRequestDTO;
import com.example.BAS.dto.collection.CollectionResponseDTO;
import com.example.BAS.dto.file.ImageDTO;
import com.example.BAS.entitiy.blog.Blogs;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.files.CollectionFiles;
import com.example.BAS.entitiy.files.CollectionImages;
import com.example.BAS.repository.CollectionFilesRepository;
import com.example.BAS.repository.CollectionImageRepository;
import com.example.BAS.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionDAO collectionDAO;
    private final FileService fileService;
    private final CollectionImageRepository collectionImageRepository;
    private final CollectionFilesRepository collectionFilesRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public CollectionRequestDTO save(Long blogId, CollectionResponseDTO collectionResponseDTO, String membershipType, Long tierId) throws IOException {
        ModelMapper mapper = new ModelMapper();
        Collections collections = mapper.map(collectionResponseDTO, Collections.class);

        Blogs blog = new Blogs();
        blog.setBlogId(blogId);
        collections.setBlogs(blog);

        if (collectionResponseDTO.getCollectionImages() != null && !collectionResponseDTO.getCollectionImages().isEmpty()) {
            List<CollectionImages> collectionImages = saveCollectionImages(collectionResponseDTO.getCollectionImages());
            for (CollectionImages images : collectionImages) {
                if (images != null) {
                    collections.setCollectionImages(collectionImages);
                    images.setCollections(collections);
                }
            }
        }

        if (collectionResponseDTO.getCollectionFiles() != null) {
            CollectionFiles collectionFile = saveCollectionFile(collectionResponseDTO.getCollectionFiles());
            if (collectionFile != null) {
                collections.setCollectionFiles(collectionFile);
                collectionFile.setCollections(collections);
            }
        }

        Collections savedCollection = collectionDAO.save(collections);

        CollectionRequestDTO collectionRequestDTO = new CollectionRequestDTO();
        collections.setCollectionId(savedCollection.getCollectionId());
        collections.setBlogs(savedCollection.getBlogs());
        collections.setContent(savedCollection.getContent());
        collections.setTitle(savedCollection.getTitle());
        collections.setMembershipType(savedCollection.getMembershipType());
        collections.setTierId(tierId);

        return collectionRequestDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollectionRequestDTO> getCollectionList(Long blogId) {
        List<Collections> collections = collectionDAO.findByBlogs_BlogId(blogId);
        return collections.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ByteArrayResource downloadFiles(String fileUrl) throws IOException {
        Path downloadPath = Paths.get(uploadPath, "collectionFiles", fileUrl);

        if (Files.exists(downloadPath)) {
            byte[] fileContent = Files.readAllBytes(downloadPath);

            return new ByteArrayResource(fileContent);
        } else {

            return null;
        }
    }

    private CollectionRequestDTO convertToDTO(Collections collectionEntity) {
        ModelMapper modelMapper = new ModelMapper();
        CollectionRequestDTO dto = modelMapper.map(collectionEntity, CollectionRequestDTO.class);

        List<String> imageUrls = collectionEntity.getCollectionImages().stream()
                .map(CollectionImages::getFileUrl)
                .collect(Collectors.toList());
        dto.setImageUrls(imageUrls);

        String collectionFilesUrl = "";
        if (collectionEntity.getCollectionFiles() != null) {
            collectionFilesUrl = collectionEntity.getCollectionFiles().getFileUrl();
        }
        dto.setFileUrl(collectionFilesUrl);

        return dto;
    }

    private List<CollectionImages> saveCollectionImages(List<MultipartFile> collectionImages) throws IOException {
        List<CollectionImages> savedImages = new ArrayList<>();
        for (MultipartFile file : collectionImages) {
            if (file != null && !file.isEmpty()) {
            String originalName = file.getOriginalFilename();
            Path root = Paths.get(uploadPath, "collectionImages");
            ImageDTO imageDTO = fileService.createImageDTO(originalName, root);

            String relativeFileUrl = "/img/collectionImages/" + imageDTO.getUuid() + "_" + imageDTO.getFileName();

            CollectionImages collectionImage = CollectionImages.builder()
                    .uuid(imageDTO.getUuid())
                    .fileName(imageDTO.getFileName())
                    .fileUrl(relativeFileUrl)
                    .build();

            file.transferTo(Paths.get(imageDTO.getFileUrl()));
            savedImages.add(collectionImageRepository.save(collectionImage));
            } else {
                savedImages.add(null);
            }
        }

        return savedImages;
    }

    private CollectionFiles saveCollectionFile(MultipartFile collectionFile) throws IOException {
        if (!collectionFile.isEmpty()) {
            Path root = Paths.get(uploadPath, "collectionFiles");

            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }

            String originalFileName = collectionFile.getOriginalFilename();

            String uuid = UUID.randomUUID().toString();

            Path filePath = root.resolve(uuid + "_" + originalFileName);

            collectionFile.transferTo(filePath);

            CollectionFiles collectionFiles = CollectionFiles.builder()
                    .uuid(uuid)
                    .fileName(originalFileName)
                    .fileUrl("/img/collectionFiles/" + uuid + "_" + originalFileName)
                    .build();

            return collectionFilesRepository.save(collectionFiles);
        }

        return null;
    }


    @Override
    public List<String> getCollectionImagesUrls(Long collectionId) {
        Collections collectionsEntity = collectionDAO.findById(collectionId);
        List<String> imageUrls = new ArrayList<>();
        if (collectionsEntity != null && collectionsEntity.getCollectionImages() != null) {
            for (CollectionImages collectionImages : collectionsEntity.getCollectionImages()) {
                imageUrls.add(collectionImages.getFileUrl());
            }
        }
        return imageUrls;
    }

    @Override
    public String getCollectionFileName(Long collectionId) {
        Collections collections = collectionDAO.findById(collectionId);
        if (collections != null && collections.getCollectionFiles() != null) {
            return collections.getCollectionFiles().getFileName();
        }
        return null;
    }

    @Override
    public String getCollectionFileUuid(Long collectionId) {
        Collections collections = collectionDAO.findById(collectionId);
        if (collections != null && collections.getCollectionFiles() != null) {
            return collections.getCollectionFiles().getUuid();
        }
        return null;
    }

    @Override
    public InputStreamResource downloadFile(String fileName) throws IOException {
        Path downloadPath = Paths.get(uploadPath, "collectionFiles", fileName);

        if (Files.exists(downloadPath)) {
            return new InputStreamResource(Files.newInputStream(downloadPath));
        } else {
            return null;
        }
    }
}
