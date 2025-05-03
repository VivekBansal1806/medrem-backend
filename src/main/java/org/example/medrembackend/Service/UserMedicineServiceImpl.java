package org.example.medrembackend.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.example.medrembackend.DTOs.UserMedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineResponse;
import org.example.medrembackend.Entity.Medicine;
import org.example.medrembackend.Entity.UserEntity;
import org.example.medrembackend.Entity.UserMedicine;
import org.example.medrembackend.Repository.MedicineRepo;
import org.example.medrembackend.Repository.UserMedicineRepo;
import org.example.medrembackend.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMedicineServiceImpl implements UserMedicineService {

    private final Logger logger = LoggerFactory.getLogger(UserMedicineServiceImpl.class);

    private final UserMedicineRepo userMedicineRepo;
    private final UserRepo userRepo;
    private final MedicineRepo medicineRepo;

    @Autowired
    public UserMedicineServiceImpl(UserMedicineRepo userMedicineRepo, UserRepo userRepo, MedicineRepo medicineRepo) {
        this.userMedicineRepo = userMedicineRepo;
        this.userRepo = userRepo;
        this.medicineRepo = medicineRepo;
    }

    // Add a new UserMedicine
    public UserMedicineResponse addUserMedicine(UserMedicineRequest request) {
        logger.info("Adding UserMedicine for userId: {} and medicineId: {}", request.getUserId(), request.getMedicineId());
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
            logger.info("UserMedicine saved with id: {}", savedUserMedicine.getId());

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
            logger.error("User or Medicine not found for userId: {} and medicineId: {}", request.getUserId(), request.getMedicineId());
            throw new RuntimeException("User or Medicine not found");
        }
    }

    // Get all UserMedicines for a user
    public List<UserMedicineResponse> getUserMedicines(Long userId) {
        logger.info("Fetching UserMedicines for userId: {}", userId);
        List<UserMedicine> userMedicines = userMedicineRepo.findByUser_UserId(userId);

        List<UserMedicineResponse> responseList = userMedicines.stream().map(userMedicine -> UserMedicineResponse.builder()
                .id(userMedicine.getId())
                .userId(userMedicine.getUser().getUserId())
                .medicineId(userMedicine.getMedicine().getMedicineId())
                .medicineName(userMedicine.getMedicine().getName())
                .quantityPacks(userMedicine.getQuantityPacks())
                .pillsPerPack(userMedicine.getPillsPerPack())
                .addedDate(userMedicine.getAddedDate())
                .build()
        ).toList();
        logger.info("Fetched {} UserMedicines for userId: {}", responseList.size(), userId);
        return responseList;
    }

    // Find a specific UserMedicine
    public UserMedicineResponse getUserMedicine(Long userId, Long medicineId) {
        logger.info("Fetching UserMedicine for userId: {} and medicineId: {}", userId, medicineId);
        Optional<UserMedicine> userMedicine = userMedicineRepo.findByUser_UserIdAndMedicine_MedicineId(userId, medicineId);

        if (userMedicine.isPresent()) {
            UserMedicine um = userMedicine.get();
            logger.info("UserMedicine found with id: {}", um.getId());
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
            logger.error("UserMedicine not found for userId: {} and medicineId: {}", userId, medicineId);
            throw new RuntimeException("UserMedicine not found");
        }
    }
}
