package com.example.BAS.entitiy.blog;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoostHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long boostHistoryId;

    private Boolean isBoostState;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blogs blogs;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    @ToString.Exclude
    private Membership_tier membership_tier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Users user;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime boostDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime cancelDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime expirationDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;

    @PrePersist
    public void createDate() {
        this.boostDate = LocalDateTime.now();
    }
    @PreUpdate
    public void updateDate() {
        this.updateDate = LocalDateTime.now();
    }
    @PreRemove
    public void deleteDate() {
        this.cancelDate = LocalDateTime.now();
    }

}
