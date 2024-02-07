package com.example.DevSculpt.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponseDTO {
    private Long alarmId;
    private String message;
    private LocalDateTime creationDate;
}
