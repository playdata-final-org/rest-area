package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.Collections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionDAO {
    Collections save(Collections collections);

   Page<Collections> findByBlogs_BlogId(Long blogId, Pageable pageable);

    Collections findById(Long collectionId);

    int getCollectionCount(Long blogId);

    Collections findByCollectionId(Long collectionId);

    Collections findByCollectionIds(Long collectionId);
}
