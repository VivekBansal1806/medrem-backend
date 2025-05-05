package org.example.medrembackend.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "medicines")
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId; // Medicine ID

    @Column(nullable = false)
    private String medicineName; // Name of the medicine

    @Column(name = "pack_size", nullable = false)
    private Integer pillsPerPack; // Pack size (e.g., number of pills in a pack)

    private String price; // Price of the medicine

    private String manufacturer; // Manufacturer of the medicine

    @Enumerated(EnumType.STRING)
    private MedicineType medicineType; // Type of the medicine (e.g., Tablet, Syrup)

    @Column(name = "composition_1")
    private String composition1; // Composition 1 (Active ingredient)

    @Column(name = "composition_2")
    private String composition2; // Composition 2 (if applicable)

    @Lob
    private String about;

    public enum MedicineType {
        MEDICINE,
        TABLET,
        CAPSULE,
        SYRUP,
        INJECTION,
        OINTMENT,
        DROPS,
        INHALER
    }

}
