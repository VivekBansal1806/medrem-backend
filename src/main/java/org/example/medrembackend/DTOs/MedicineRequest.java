package org.example.medrembackend.DTOs;

import lombok.Data;

@Data
public class MedicineRequest {

    private String name;
    private Integer pillsPerPack;
    private String price;
    private String manufacturer;
    private String medicineType;
    private String composition1;
    private String composition2;
    private String about;
}
