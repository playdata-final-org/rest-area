package com.example.BAS.entitiy.payment;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Charge {
    @Id
    @GeneratedValue
    private Long chargeId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Users user;
    private int amount;
}
