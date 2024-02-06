package com.example.BAS.service.collection;

import com.example.BAS.dto.collection.UserComment;
import com.example.BAS.entitiy.blog.CollectionComment;

import java.util.List;

public interface CommentService {
    CollectionComment save(UserComment userComment);

    List<CollectionComment> findByCollectionId(Long collectionId);
}
