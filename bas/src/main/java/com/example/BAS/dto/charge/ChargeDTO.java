package com.example.BAS.dto.charge;


import com.example.BAS.entitiy.users.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChargeDTO {
    private Long chargeId;
    private int amount;
    private Users users;

    public ChargeDTO(int amount) {
        this.amount = amount;
    }
}
