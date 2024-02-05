package com.example.BAS.entitiy.blog;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CollectionComment {
    @Id
    @GeneratedValue
    private Long commentId;
    @Column(length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collections collections;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}
