package com.example.BAS.service.collection;

import com.example.BAS.dao.collection.CommentDAO;
import com.example.BAS.dto.collection.UserComment;
import com.example.BAS.entitiy.blog.CollectionComment;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;
import com.example.BAS.service.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentDAO commentDAO;
    private final UsersService usersService;
    private final CollectionService collectionService;


    @Override
    public CollectionComment save(UserComment userComment) {
        Users users = usersService.findByUserId(userComment.getUserId());
        Collections collections = collectionService.findByCollectionId(userComment.getCollectionId());
        String content = userComment.getContent();
        return commentDAO.save(users, collections, content);
    }

}
