package org.example.medrembackend.Repository;

import org.example.medrembackend.Entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReminderRepo extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserMedicine_UserMedicineId(Long userMedicineId);
}
