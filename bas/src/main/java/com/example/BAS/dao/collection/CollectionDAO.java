package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.Collections;

import java.util.List;

public interface CollectionDAO {
    Collections save(Collections collections);

    List<Collections> findByBlogs_BlogId(Long blogId);

    Collections findById(Long collectionId);
}
