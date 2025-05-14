package org.example.medrembackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customMedicineId;

    @Column(nullable = false)
    private String customMedicineName; // Name of the medicine

    @Column(name = "pack_size")
    private Integer pillsPerPack; // Pack size (e.g., number of pills in a pack)

    private String price; // Price of the medicine

    private String manufacturer; // Manufacturer of the medicine

    @Enumerated(EnumType.STRING)
    private MedicineType customMedicineType; // Type of the medicine (e.g., Tablet, Syrup)

    @Column(name = "composition_1")
    private String composition1; // Composition 1 (Active ingredient)

    @Column(name = "composition_2")
    private String composition2; // Composition 2 (if applicable)

    @Lob
    private String about;


}

