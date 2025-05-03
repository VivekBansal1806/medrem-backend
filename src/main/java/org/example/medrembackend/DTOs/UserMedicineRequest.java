package org.example.medrembackend.DTOs;

import lombok.Data;

import java.time.LocalDate;

@Data

public class UserMedicineRequest {

    private Long userId;          // The user who is adding the medicine
    private Long medicineId;      // The medicine that is being added
    private Integer quantityPacks;
    private Integer pillsPerPack;
    private LocalDate addedDate;  // When the medicine is added
}
