package org.example.medrembackend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineRequest {

    private String medicineName;
    private Integer pillsPerPack;
    private String price;
    private String manufacturer;
    private String medicineType;
    private String composition1;
    private String composition2;
    private String about;
}
