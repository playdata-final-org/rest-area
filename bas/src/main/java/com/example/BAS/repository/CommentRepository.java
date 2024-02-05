package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.CollectionComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CollectionComment,Long> {
    List<CollectionComment> findByCollectionsCollectionId(Long collectionId);
}
