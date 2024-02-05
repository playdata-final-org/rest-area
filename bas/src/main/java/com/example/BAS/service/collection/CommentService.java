package com.example.BAS.service.collection;

import com.example.BAS.dto.collection.UserComment;
import com.example.BAS.entitiy.blog.CollectionComment;

public interface CommentService {
    CollectionComment save(UserComment userComment);

}
