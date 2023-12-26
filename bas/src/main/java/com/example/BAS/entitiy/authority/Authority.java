package com.example.BAS.entitiy.authority;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue
    private Long authorityId;
    @Column(length = 50)
    private String authorityName;
}
