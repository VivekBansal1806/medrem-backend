package org.example.medrembackend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderResponse {
    private Long reminderId;
    private Long userMedicineId;
    private LocalDateTime reminderTime;
    private String frequency;
    private boolean enabled;
}
