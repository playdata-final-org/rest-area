package com.example.BAS.dto.blog;

import com.example.BAS.entitiy.blog.Categories;
import com.example.BAS.entitiy.blog.Collections;
import com.example.BAS.entitiy.blog.Memberships;
import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    private Categories categories;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private LocalDateTime deleteDate;
}
