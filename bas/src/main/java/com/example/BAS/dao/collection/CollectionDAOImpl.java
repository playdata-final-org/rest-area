package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectionDAOImpl implements CollectionDAO {
    private final CollectionRepository collectionRepository;

    @Override
    public Collections save(Collections collections) {
        return collectionRepository.save(collections);
    }

    @Override
    public List<Collections> findByBlogs_BlogId(Long blogId) {
        return collectionRepository.findByBlogs_BlogId(blogId);
    }

    @Override
    public Collections findById(Long collectionId) {
        return collectionRepository.findByCollectionId(collectionId);
    }
}


