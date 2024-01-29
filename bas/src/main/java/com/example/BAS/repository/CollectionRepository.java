package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.Collections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collections,Long> {
    List<Collections> findByBlogs_BlogId(Long blogId);

    Collections findByCollectionId(Long collectionId);
    @Query("SELECT COUNT(DISTINCT c.collectionId) FROM Collections c WHERE c.blogs.blogId = :blogId")
    int countDistinctCollectionIdsByBlogId(@Param("blogId") Long blogId);
}
