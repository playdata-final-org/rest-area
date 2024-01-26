package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.Collections;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collections,Long> {
    List<Collections> findByBlogs_BlogId(Long blogId);

    Collections findByCollectionId(Long collectionId);
}
