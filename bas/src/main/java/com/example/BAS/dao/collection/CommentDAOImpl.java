package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.CollectionComment;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentDAOImpl implements CommentDAO{
    private final CommentRepository commentRepository;

    @Override
    public CollectionComment save(Users users, Collections collections, String content) {
        CollectionComment comment = new CollectionComment();
        comment.setUsers(users);
        comment.setCollections(collections);
        comment.setContent(content);
        return commentRepository.save(comment);
    }
}
