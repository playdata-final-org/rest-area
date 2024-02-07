package com.example.DevSculpt.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRequestDTO {
    private Long commentId;
    private String message;
}
