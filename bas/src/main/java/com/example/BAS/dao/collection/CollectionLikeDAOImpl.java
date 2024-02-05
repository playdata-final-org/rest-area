package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.CollectionLike;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.CollectionLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectionLikeDAOImpl implements CollectionLikeDAO{
    private final CollectionLikeRepository collectionLikeRepository;
    @Override
    public void save(CollectionLike like) {
        collectionLikeRepository.save(like);
    }

    @Override
    public void deleteByUserAndPost(Users user, Collections collections) {
        collectionLikeRepository.deleteByUserAndCollections(user,collections);
    }

    @Override
    public List<CollectionLike> findByCollections(Collections collections) {
        return collectionLikeRepository.findByCollections(collections);
    }
}
