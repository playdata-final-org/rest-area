package com.example.BAS.dto.blog;

import com.example.BAS.entitiy.blog.BlogCategory;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.blog.Memberships;
import com.example.BAS.entitiy.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogRequestDTO {

    private Long blogId;

    private Users users;

    private List<Collections> collections;

    private List<Memberships> memberships;

    private BlogCategory category;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deleteDate;
}
