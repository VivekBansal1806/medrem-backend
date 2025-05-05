package org.example.medrembackend.Service;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


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
    public UserMedicineResponse addUserMedicine(UserMedicineRequest request, Long userId, Long medicineId) {
        logger.info("Adding UserMedicine for userId: {} and medicineId: {}", userId, medicineId);

        Optional<UserEntity> user = userRepo.findById(userId);
        if (user.isEmpty()) {
            logger.error("User not found for userId: {}", userId);
            throw new RuntimeException("User not found");
        }

        Optional<Medicine> medicine = medicineRepo.findById(medicineId);

        UserMedicine userMedicine = new UserMedicine();
        userMedicine.setUser(user.get());
        userMedicine.setQuantityPacks(request.getQuantityPacks());
        userMedicine.setPillsPerPack(request.getPillsPerPack());
        userMedicine.setAddedDate(LocalDate.now());

        if (medicine.isPresent()) {
            userMedicine.setMedicine(medicine.get());
        } else {
            userMedicine.setMedicineName(request.getMedicineName());
            userMedicine.setManufacturer(request.getManufacturer());
            userMedicine.setComposition1(request.getComposition1());
            userMedicine.setComposition2(request.getComposition2());
            userMedicine.setPrice(request.getPrice());
            userMedicine.setType(request.getMedicineType());
            userMedicine.setAbout(request.getAbout());
        }

        UserMedicine savedUserMedicine = userMedicineRepo.save(userMedicine);
        logger.info("UserMedicine saved with id: {}", savedUserMedicine.getUserMedicineId());

        return UserMedicineResponse.builder()
                .userMedicineId(savedUserMedicine.getUserMedicineId())
                .userId(savedUserMedicine.getUser().getUserId())
                .medicineId(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getMedicineId() : null)
                .medicineName(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getMedicineName() : savedUserMedicine.getMedicineName())
                .manufacturer(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getManufacturer() : savedUserMedicine.getManufacturer())
                .composition1(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getComposition1() : savedUserMedicine.getComposition1())
                .composition2(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getComposition2() : savedUserMedicine.getComposition2())
                .price(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getPrice() : savedUserMedicine.getPrice())
                .medicineType(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getMedicineType().name() : savedUserMedicine.getType())
                .about(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getAbout() : savedUserMedicine.getAbout())
                .quantityPacks(savedUserMedicine.getQuantityPacks())
                .pillsPerPack(savedUserMedicine.getPillsPerPack())
                .addedDate(savedUserMedicine.getAddedDate())
                .build();
    }

    // Get all UserMedicines for a user
    public List<UserMedicineResponse> getUserMedicines(Long userId) {
        logger.info("Fetching UserMedicines for userId: {}", userId);
        List<UserMedicine> userMedicines = userMedicineRepo.findByUser_UserId(userId);

        List<UserMedicineResponse> responseList = userMedicines.stream().map(userMedicine -> UserMedicineResponse.builder()
                .userMedicineId(userMedicine.getUserMedicineId())
                .userId(userMedicine.getUser().getUserId())
                .medicineId(userMedicine.getMedicine().getMedicineId())
                .medicineName(userMedicine.getMedicine().getMedicineName())
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
            logger.info("UserMedicine found with id: {}", um.getUserMedicineId());
            return UserMedicineResponse.builder()
                    .userMedicineId(um.getUserMedicineId())
                    .userId(um.getUser().getUserId())
                    .medicineId(um.getMedicine().getMedicineId())
                    .medicineName(um.getMedicine().getMedicineName())
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
