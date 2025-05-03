package org.example.medrembackend.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicineResponse {

    private Long id;
    private Long userId;
    private Long medicineId;
    private String medicineName;
    private Integer quantityPacks;
    private Integer pillsPerPack;
    private LocalDate addedDate;
}
