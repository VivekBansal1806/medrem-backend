package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.UserMedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineResponse;

import java.util.List;

public interface UserMedicineService {
    public UserMedicineResponse addUserMedicine(UserMedicineRequest request);

    public List<UserMedicineResponse> getUserMedicines(Long userId);

    public UserMedicineResponse getUserMedicine(Long userId, Long medicineId);
}
