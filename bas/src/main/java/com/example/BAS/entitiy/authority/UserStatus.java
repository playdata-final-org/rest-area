package com.example.BAS.entitiy.authority;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.engine.internal.Cascade;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class UserStatus {
    @Id
    @GeneratedValue
    private Long userStatusId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authorities_id")
    @ToString.Exclude
    private Authorities authorities;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;


}
