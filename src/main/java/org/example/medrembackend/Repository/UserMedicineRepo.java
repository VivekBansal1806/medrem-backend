package org.example.medrembackend.Repository;

import org.example.medrembackend.Entity.Medicine;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Entity.UserMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMedicineRepo extends JpaRepository<UserMedicine, Long> {

    // Find all medicines of a specific user
    List<UserMedicine> findByUser_UserId(Long userId);  // Reference to user.userId instead of userId

    // Find a UserMedicine by user and medicine IDs
    Optional<UserMedicine> findByUser_UserIdAndMedicine_MedicineId(Long userId, Long medicineId);  // Reference to user.userId and medicine.id

    Optional<UserMedicine> findByUserAndMedicine(UserEntity user, Medicine medicine);
}
