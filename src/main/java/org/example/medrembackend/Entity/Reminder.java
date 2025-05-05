package org.example.medrembackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ReminderId;

    @ManyToOne
    @JoinColumn(name = "user_medicine_id")
    private UserMedicine userMedicine;

    private LocalDateTime reminderTime;

    private String frequency; // e.g., "DAILY", "WEEKLY"

    private boolean enabled;
}
