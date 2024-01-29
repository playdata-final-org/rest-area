package com.example.BAS.dto.blog;

import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.blog.Memberships;
import com.example.BAS.entitiy.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogResponseDTO {
    private Long blogId;

    private Users users;

    private List<Collections> collections;

    private List<Memberships> memberships;

    private BlogCategory category;
}
