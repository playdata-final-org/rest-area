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
public class Memberships {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long membershipId;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blogs blogs;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Users creator;

    @OneToMany(mappedBy = "memberships",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Membership_tier> membershipTiers = new ArrayList<>();
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deleteDate;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }

    public void addMembershipTier (Membership_tier membership_tier){
        if (!membershipTiers.contains(membership_tier)) {
            membershipTiers.add(membership_tier);
            membership_tier.setMemberships(this);
        }
    }

    }