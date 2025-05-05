package org.example.medrembackend.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReminderRequest {
    private Long userMedicineId;
    private LocalDateTime reminderTime;
    private String frequency;
    private boolean enabled;
}
