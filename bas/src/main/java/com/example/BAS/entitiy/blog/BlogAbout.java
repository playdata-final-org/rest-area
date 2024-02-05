package com.example.BAS.entitiy.blog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BlogAbout {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long aboutId;

    private String aboutTitle;
    @Column(length = 1000)
    private String aboutContent;

    @ManyToOne
    @JoinColumn(name = "blogId")
    private Blogs blogs;

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
