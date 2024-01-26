package com.example.BAS.entitiy.authority;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Authorities {
    @Id
    @GeneratedValue
    private Long authoritiesId;

    private String boosterAuthority;

    private String creatorAuthority;

    @OneToMany(mappedBy = "authorities")
    private List<UserStatus> userStatuses;
}
