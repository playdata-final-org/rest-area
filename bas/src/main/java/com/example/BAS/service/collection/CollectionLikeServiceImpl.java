package com.example.BAS.service.collection;

import com.example.BAS.dao.collection.CollectionLikeDAO;
import com.example.BAS.entitiy.blog.CollectionLike;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CollectionLikeServiceImpl implements CollectionLikeService{

    private final CollectionLikeDAO collectionLikeDAO;
    @Override
    public void addLike(Users user, Collections collections) {
        CollectionLike like = new CollectionLike();
        like.setUser(user);
        like.setCollections(collections);
        collectionLikeDAO.save(like);
    }

    @Override
    public void removeLike(Users user, Collections collections) {
        collectionLikeDAO.deleteByUserAndPost(user, collections);
    }

    @Override
    public List<CollectionLike> findByCollections(Collections collections) {
        return collectionLikeDAO.findByCollections(collections);
    }
}
