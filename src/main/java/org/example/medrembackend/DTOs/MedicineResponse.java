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
    private String name;
    private Integer packSize;
    private Double price;
    private String manufacturer;
    private String type;
    private String composition1;
    private String composition2;
}
