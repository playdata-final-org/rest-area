package com.example.BAS.entitiy.payment;

import com.example.BAS.entitiy.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PointPaymentHistory {
    @Id
    @GeneratedValue
    private Long pointHistoryId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "amount_charged")
    private int amountCharged;

    @Column(name = "charged_points")
    private int chargedPoints;

    @Column(name = "date_time")
    private LocalDateTime createDate;

}
