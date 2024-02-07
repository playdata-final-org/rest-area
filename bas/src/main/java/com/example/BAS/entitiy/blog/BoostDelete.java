package com.example.BAS.entitiy.blog;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BoostDelete {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boostDeleteId;

    private Boolean isBoostState;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blogs blogs;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Membership_tier membership_tier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime boostDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime cancelDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime expirationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;

    public void create(BoostHistory boostHistory) {
        this.boostDate = boostHistory.getBoostDate();
        this.blogs=boostHistory.getBlogs();
        this.isBoostState=boostHistory.getIsBoostState();
        this.user = boostHistory.getUser();
        this.expirationDate = boostHistory.getExpirationDate();
        this.updateDate = boostHistory.getUpdateDate();
        this.cancelDate = boostHistory.getCancelDate();
        this.membership_tier=boostHistory.getMembership_tier();
    }
}