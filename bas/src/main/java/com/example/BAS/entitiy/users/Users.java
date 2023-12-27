package com.example.BAS.entitiy.users;

import com.example.BAS.entitiy.authority.Authority;
import com.example.BAS.entitiy.image.ProfileImage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Users {
    @JsonIgnore
    @Id
    @GeneratedValue
    private Long userId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private ProfileImage profileImage;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private int coin;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updateDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime deleteDate;

    private boolean userstate;

    @PrePersist //insert되기 전에 실행 = DB에 값을 넣으면 자동으로 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate //update되기 전에 실행
    public void updateDate() {
        this.updateDate = LocalDateTime.now();
    }

    @PreRemove //delete되기 전에 실행
    public void deleteDate() {
        this.deleteDate = LocalDateTime.now();
    }

    public void updateProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }



}
