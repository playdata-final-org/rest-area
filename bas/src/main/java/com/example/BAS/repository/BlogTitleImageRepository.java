package com.example.BAS.repository;

import com.example.BAS.entitiy.files.BlogTitleImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogTitleImageRepository extends JpaRepository<BlogTitleImages,Long> {

    BlogTitleImages findByBlogs_BlogId(Long blogId);
}
