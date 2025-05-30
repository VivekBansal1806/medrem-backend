package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineResponse;

import java.util.List;

public interface UserMedicineService {

//    @Deprecated
//    UserMedicineResponse addUserMedicine(UserMedicineRequest request, long userId, Long medicineId);

    UserMedicineResponse addMedicineToUserMedicine(long userId, Long medicineId);

    UserMedicineResponse createNewUserMedicine(MedicineRequest request, long userId);

    List<UserMedicineResponse> getUserMedicines(Long userId);

    List<UserMedicineResponse> getUserMedicines(String username);

    UserMedicineResponse getUserMedicineById(Long userMedicineId);

    void deleteUserMedicine(Long userMedicineId);

    void updateRemainingPills(Long userMedicineId, int newQuantity);

    void takeMedicine(Long userMedicineId, int pillsTaken);

    UserMedicineResponse updateUserMedicine(MedicineRequest userMedicineRequest, Long userMedicineId);
}
