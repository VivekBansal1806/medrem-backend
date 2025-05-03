package org.example.medrembackend.DTOs;

import lombok.Data;

@Data
public class MedicineRequest {
    private String name;
    private Integer packSize;
    private Double price;
    private String manufacturer;
    private String type;
    private String composition1;
    private String composition2;
}
