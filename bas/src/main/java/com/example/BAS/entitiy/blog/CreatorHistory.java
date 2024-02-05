package com.example.BAS.entitiy.blog;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CreatorHistory {
    @Id
    @GeneratedValue
    private Long creatorHistoryId;

    private Boolean isBoostState;

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
}

