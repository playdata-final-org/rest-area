package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionRepository extends JpaRepository<Collections,Long> {
    

    Collections findByCollectionId(Long collectionId);
    @Query("SELECT COUNT(DISTINCT c.collectionId) FROM Collections c WHERE c.blogs.blogId = :blogId")
    int countDistinctCollectionIdsByBlogId(@Param("blogId") Long blogId);
    Page<Collections> findByBlogs_BlogId(Long blogId, Pageable pageable);

}
