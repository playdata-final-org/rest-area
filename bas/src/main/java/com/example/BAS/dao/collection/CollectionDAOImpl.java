package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CollectionDAOImpl implements CollectionDAO {
    private final CollectionRepository collectionRepository;

    @Override
    public Collections save(Collections collections) {
        return collectionRepository.save(collections);
    }

    @Override
    public Page<Collections> findByBlogs_BlogId(Long blogId, Pageable pageable) {
        return collectionRepository.findByBlogs_BlogId(blogId, pageable);
    }

    @Override
    public Collections findById(Long collectionId) {
        return collectionRepository.findByCollectionId(collectionId);
    }

    @Override
    public int getCollectionCount(Long blogId) {
        return collectionRepository.countDistinctCollectionIdsByBlogId(blogId);
    }

    @Override
    public Collections findByCollectionId(Long collectionId) {
        return collectionRepository.findByCollectionId(collectionId);
    }

    @Override
    public Collections findByCollectionIds(Long collectionId) {
        return collectionRepository.findByCollectionId(collectionId);
    }
}


