package org.example.medrembackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMedicineId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    // if medicine is not found in medicine
    private String medicineName;
    private String manufacturer;
    private String composition1;
    private String composition2;
    private String price;
    private String type;

    private Integer pillsPerPack;   // e.g., 15 pills per pack

    @Lob
    private String about;

    private Integer quantityPacks;  // e.g., 2 packs
    private LocalDate addedDate;
}
