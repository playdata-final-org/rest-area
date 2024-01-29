package com.example.BAS.entitiy.blog;


import com.example.BAS.entitiy.users.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DynamicUpdate
public class Blogs {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long blogId;

    private int countBoosters;
    private int countCollections;

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

    @Enumerated(EnumType.STRING)
    private BlogCategory category;

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
