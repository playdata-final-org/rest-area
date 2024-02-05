package com.example.BAS.repository;

import com.example.BAS.entitiy.blog.CollectionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CollectionComment,Long> {
}
