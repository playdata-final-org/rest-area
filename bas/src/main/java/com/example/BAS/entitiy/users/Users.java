package com.example.BAS.entitiy.users;

import com.example.BAS.entitiy.authority.UserStatus;
import com.example.BAS.entitiy.blog.*;
import com.example.BAS.entitiy.files.ProfileImage;
import com.example.BAS.entitiy.payment.Charge;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Users {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    private String username;
    @JsonIgnore
    private String password;
    private String nickName;
    private int point;

    @OneToMany(mappedBy = "users")
    @ToString.Exclude
    private List<CollectionComment> collectionComments;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<CollectionLike> collectionLikes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    @ToString.Exclude
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BoostHistory> boostHistories;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<UserStatus> userStatuses = new ArrayList<>();

    private String currentRole;

    @OneToMany(mappedBy = "users")
    @ToString.Exclude
    private List<Blogs> blogs;

    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Membership_tier membership_tier;
    @OneToMany(mappedBy = "creator")
    @ToString.Exclude
    private List<Memberships> memberships;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Charge> charges;

    private boolean userState;

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

    public void updateProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

}
