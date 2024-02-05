package com.example.BAS.service.collection;

import com.example.BAS.entitiy.blog.CollectionLike;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;

import java.util.List;

public interface CollectionLikeService {
    void addLike(Users user, Collections collections);

    void removeLike(Users user, Collections collections);

    List<CollectionLike> findByCollections(Collections collections);
}
