package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;

import java.util.List;

public interface MedicineService {
    MedicineResponse addMedicine(MedicineRequest request);

    List<MedicineResponse> getAllMedicines();

    MedicineResponse getMedicineByMedicineId(Long id);

    List<MedicineResponse> searchMedicinesByMedicineName(String name);

}
