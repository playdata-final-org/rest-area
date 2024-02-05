package com.example.BAS.dao.collection;

import com.example.BAS.entitiy.blog.CollectionComment;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.users.Users;

public interface CommentDAO {
    CollectionComment save(Users users, Collections collections, String content);

}
