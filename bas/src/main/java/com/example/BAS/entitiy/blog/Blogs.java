package com.example.BAS.entitiy.blog;


import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Blogs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long blogId;

    @OneToMany(mappedBy = "blogs")
    @ToString.Exclude
    private List<BoostHistory> boostHistories;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Users users;

    @OneToMany(mappedBy = "blogs")
    @ToString.Exclude
    private List<Collections> collections;

    @OneToMany(mappedBy = "blogs")
    @ToString.Exclude
    private List<Memberships> memberships;

    @OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BlogAbout> blogAbouts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="category_id")
    @ToString.Exclude
    private Categories categories;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deleteDate;

    @PrePersist
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }
    @PreUpdate
    public void updateDate() {
        this.updateDate = LocalDateTime.now();
    }
    @PreRemove
    public void deleteDate() {
        this.deleteDate = LocalDateTime.now();
    }
}
