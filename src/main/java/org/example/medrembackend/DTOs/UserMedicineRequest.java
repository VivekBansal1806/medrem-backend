package org.example.medrembackend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicineRequest {

    private String medicineName;
    private String manufacturer;
    private String composition1;
    private String composition2;
    private String price;
    private String medicineType;
    private String about;

    private Integer quantityPacks;
    private Integer pillsPerPack;
    private LocalDate addedDate;  // When the medicine is added
}
