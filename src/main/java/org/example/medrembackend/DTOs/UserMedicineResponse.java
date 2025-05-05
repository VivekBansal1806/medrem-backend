package org.example.medrembackend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicineResponse {

    private Long userMedicineId;
    private Long userId;
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
}
