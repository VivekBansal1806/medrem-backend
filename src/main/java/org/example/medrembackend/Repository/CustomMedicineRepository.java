package org.example.medrembackend.Repository;

import org.example.medrembackend.Entity.CustomMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomMedicineRepository extends JpaRepository<CustomMedicine, Long> {


}
