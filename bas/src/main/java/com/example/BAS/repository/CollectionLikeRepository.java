package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.CollectionLike;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollectionLikeRepository extends JpaRepository<CollectionLike,Long> {
    void deleteByUserAndCollections(Users user, Collections collections);

    List<CollectionLike> findByCollections(Collections collections);
}
