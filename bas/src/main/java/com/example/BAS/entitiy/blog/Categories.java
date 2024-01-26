package com.example.BAS.entitiy.blog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Categories {
    @Id
    @GeneratedValue
    private Long categoryId;
    private String categoryName;

    @OneToMany(mappedBy = "categories")
    @ToString.Exclude
    private List<Blogs> blogs;

}
