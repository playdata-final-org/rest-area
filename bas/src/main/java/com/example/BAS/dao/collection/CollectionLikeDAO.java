package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.CollectionLike;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;

import java.util.List;

public interface CollectionLikeDAO {
    void save(CollectionLike like);

    void deleteByUserAndPost(Users user, Collections collections);


    List<CollectionLike> findByCollections(Collections collections);
}
