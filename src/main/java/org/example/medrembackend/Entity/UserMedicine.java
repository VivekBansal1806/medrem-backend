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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private Integer quantityPacks;  // e.g., 2 packs
    private Integer pillsPerPack;   // e.g., 15 pills per pack
    private LocalDate addedDate;
}
