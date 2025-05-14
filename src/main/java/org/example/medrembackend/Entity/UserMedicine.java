package org.example.medrembackend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userMedicineId;

    /// /////////////////////////////////////////////////
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "custom_medicine_id")
    private CustomMedicine customMedicine;

    /// ////////////////////////////////////////////////////
    private Integer quantityPacks;  // e.g., 2 packs
    private LocalDate addedDate;
    private Integer remainingPills;
    private Integer pillsTaken;

    /// ///////////////////////////////////////////////////
    @OneToMany(mappedBy = "userMedicine", cascade = CascadeType.ALL)
    private List<Reminder> reminder;

    /// //////////////////////////////////////////////////
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


