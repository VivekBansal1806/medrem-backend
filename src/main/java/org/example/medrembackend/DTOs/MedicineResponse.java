package org.example.medrembackend.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicineResponse {
    private Long medicineId;
    private String medicineName;
    private Integer pillsPerPack;
    private String price;
    private String manufacturer;
    private String medicineType;
    private String composition1;
    private String composition2;
    private String about;
}
