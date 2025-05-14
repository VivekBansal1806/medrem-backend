package org.example.medrembackend.DTOs;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicineResponse {

    private Long userMedicineId;
    private Long medicineId;
    private String medicineName;
    private String manufacturer;
    private String composition1;
    private String composition2;
    private String price;
    private String medicineType;
    private String about;
    private Integer quantityPacks;
    private Integer pillsPerPack;
    private LocalDate addedDate;

    private Integer remainingPills;
    private Integer pillsTaken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
