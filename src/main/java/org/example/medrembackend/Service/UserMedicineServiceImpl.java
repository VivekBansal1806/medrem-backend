package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.UserMedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineResponse;
import org.example.medrembackend.Entity.Medicine;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Entity.UserMedicine;
import org.example.medrembackend.Repository.MedicineRepo;
import org.example.medrembackend.Repository.UserMedicineRepo;
import org.example.medrembackend.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserMedicineServiceImpl implements UserMedicineService {

    @Autowired
    private UserMedicineRepo userMedicineRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MedicineRepo medicineRepo;

    // Add a new UserMedicine
    public UserMedicineResponse addUserMedicine(UserMedicineRequest request) {
        Optional<UserEntity> user = userRepo.findById(request.getUserId());
        Optional<Medicine> medicine = medicineRepo.findById(request.getMedicineId());

        if (user.isPresent() && medicine.isPresent()) {
            UserMedicine userMedicine = new UserMedicine();
            userMedicine.setUser(user.get());
            userMedicine.setMedicine(medicine.get());
            userMedicine.setQuantityPacks(request.getQuantityPacks());
            userMedicine.setPillsPerPack(request.getPillsPerPack());
            userMedicine.setAddedDate(LocalDate.now());

            UserMedicine savedUserMedicine = userMedicineRepo.save(userMedicine);

            return UserMedicineResponse.builder()
                    .id(savedUserMedicine.getId())
                    .userId(savedUserMedicine.getUser().getUserId())
                    .medicineId(savedUserMedicine.getMedicine().getMedicineId())
                    .medicineName(savedUserMedicine.getMedicine().getName())
                    .quantityPacks(savedUserMedicine.getQuantityPacks())
                    .pillsPerPack(savedUserMedicine.getPillsPerPack())
                    .addedDate(savedUserMedicine.getAddedDate())
                    .build();
            
        } else {
            // Handle error if user or medicine not found
            throw new RuntimeException("User or Medicine not found");
        }
    }

    // Get all UserMedicines for a user
    public List<UserMedicineResponse> getUserMedicines(Long userId) {
        List<UserMedicine> userMedicines = userMedicineRepo.findByUser_UserId(userId);

        return userMedicines.stream().map(userMedicine -> UserMedicineResponse.builder()
                .id(userMedicine.getId())
                .userId(userMedicine.getUser().getUserId())
                .medicineId(userMedicine.getMedicine().getMedicineId())
                .medicineName(userMedicine.getMedicine().getName())
                .quantityPacks(userMedicine.getQuantityPacks())
                .pillsPerPack(userMedicine.getPillsPerPack())
                .addedDate(userMedicine.getAddedDate())
                .build()
        ).toList();
    }

    // Find a specific UserMedicine
    public UserMedicineResponse getUserMedicine(Long userId, Long medicineId) {
        Optional<UserMedicine> userMedicine = userMedicineRepo.findByUser_UserIdAndMedicine_MedicineId(userId, medicineId);

        if (userMedicine.isPresent()) {
            UserMedicine um = userMedicine.get();
            return UserMedicineResponse.builder()
                    .id(um.getId())
                    .userId(um.getUser().getUserId())
                    .medicineId(um.getMedicine().getMedicineId())
                    .medicineName(um.getMedicine().getName())
                    .quantityPacks(um.getQuantityPacks())
                    .pillsPerPack(um.getPillsPerPack())
                    .addedDate(um.getAddedDate())
                    .build();
        } else {
            // Handle error if not found
            throw new RuntimeException("UserMedicine not found");
        }
    }
}
