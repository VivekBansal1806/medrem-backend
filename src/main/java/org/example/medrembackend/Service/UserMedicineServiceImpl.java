package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineResponse;
import org.example.medrembackend.Entity.*;
import org.example.medrembackend.Repository.CustomMedicineRepository;
import org.example.medrembackend.Repository.MedicineRepo;
import org.example.medrembackend.Repository.UserMedicineRepo;
import org.example.medrembackend.Repository.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserMedicineServiceImpl implements UserMedicineService {

    private final Logger logger = LoggerFactory.getLogger(UserMedicineServiceImpl.class);

    private final UserMedicineRepo userMedicineRepo;
    private final UserRepo userRepo;
    private final MedicineRepo medicineRepo;
    private final CustomMedicineRepository customMedicineRepo;

    @Autowired
    public UserMedicineServiceImpl(UserMedicineRepo userMedicineRepo, UserRepo userRepo, MedicineRepo medicineRepo, CustomMedicineRepository customMedicineRepo) {
        this.userMedicineRepo = userMedicineRepo;
        this.userRepo = userRepo;
        this.medicineRepo = medicineRepo;
        this.customMedicineRepo = customMedicineRepo;
    }
//    @Deprecated
//    public UserMedicineResponse addUserMedicine(UserMedicineRequest request, long userId, Long medicineId) {
//        logger.info("Adding UserMedicine for username: {}", userId);
//        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        logger.info("Adding UserMedicine for username: {} and medicineId: {}", user.getUsername(), medicineId);
//
//        Optional<Medicine> medicine = medicineRepo.findById(medicineId);
//        UserMedicine userMedicine = new UserMedicine();
//
//        userMedicine.setUser(user);
//        userMedicine.setQuantityPacks(request.getQuantityPacks());
//        userMedicine.setPillsPerPack(request.getPillsPerPack());
//        userMedicine.setAddedDate(LocalDate.now());
//
//        if (medicine.isPresent()) {
//            userMedicine.setMedicine(medicine.get());
//        } else {
//            userMedicine.setMedicineName(request.getMedicineName());
//            userMedicine.setManufacturer(request.getManufacturer());
//            userMedicine.setComposition1(request.getComposition1());
//            userMedicine.setComposition2(request.getComposition2());
//            userMedicine.setPrice(request.getPrice());
//            userMedicine.setType(request.getMedicineType());
//            userMedicine.setAbout(request.getAbout());
//        }
//
//        UserMedicine savedUserMedicine = userMedicineRepo.save(userMedicine);
//        logger.info("UserMedicine saved with id: {}", savedUserMedicine.getUserMedicineId());
//
//        return UserMedicineResponse.builder()
//                .userMedicineId(savedUserMedicine.getUserMedicineId())
//                .medicineId(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getMedicineId() : null)
//                .medicineName(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getMedicineName() : savedUserMedicine.getMedicineName())
//                .manufacturer(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getManufacturer() : savedUserMedicine.getManufacturer())
//                .composition1(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getComposition1() : savedUserMedicine.getComposition1())
//                .composition2(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getComposition2() : savedUserMedicine.getComposition2())
//                .price(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getPrice() : savedUserMedicine.getPrice())
//                .medicineType(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getMedicineType().name() : savedUserMedicine.getType())
//                .about(savedUserMedicine.getMedicine() != null ? savedUserMedicine.getMedicine().getAbout() : savedUserMedicine.getAbout())
//                .quantityPacks(savedUserMedicine.getQuantityPacks())
//                .pillsPerPack(savedUserMedicine.getPillsPerPack())
//                .addedDate(savedUserMedicine.getAddedDate())
//                .build();
//    }

    public UserMedicineResponse addMedicineToUserMedicine(long userId, Long medicineId) {
        logger.info("Adding existing medicine to UserMedicine for userId: {} and medicineId: {}", userId, medicineId);

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Medicine medicine = medicineRepo.findById(medicineId)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

        // Check for existing entry
        Optional<UserMedicine> existing = userMedicineRepo.findByUserAndMedicine(user, medicine);
        if (existing.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This medicine is already added to your list.");
        }

        // Proceed to add new medicine
        UserMedicine userMedicine = new UserMedicine();
        userMedicine.setUser(user);
        userMedicine.setMedicine(medicine);
        userMedicine.setCustomMedicine(null);

        UserMedicine savedUserMedicine = userMedicineRepo.save(userMedicine);
        logger.info("UserMedicine saved with id: {}", savedUserMedicine.getUserMedicineId());

        return mapToResponse(savedUserMedicine);
    }

    public UserMedicineResponse createNewUserMedicine(MedicineRequest request, long userId) {
        logger.info("Creating new UserMedicine for userId: {}", userId);
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        //create a CustomMedicine
        CustomMedicine customMedicine = new CustomMedicine();
        customMedicine.setCustomMedicineName(request.getMedicineName());
        customMedicine.setManufacturer(request.getManufacturer());
        customMedicine.setComposition1(request.getComposition1());
        customMedicine.setComposition2(request.getComposition2());
        customMedicine.setPrice(request.getPrice());
        customMedicine.setAbout(request.getAbout());
        customMedicine.setPillsPerPack(request.getPillsPerPack());
        customMedicine.setCustomMedicineType(MedicineType.valueOf(request.getMedicineType()));
        customMedicineRepo.save(customMedicine);

        UserMedicine userMedicine = new UserMedicine();
        userMedicine.setUser(user);
        userMedicine.setCustomMedicine(customMedicine);
        userMedicine.setMedicine(null);

        UserMedicine savedUserMedicine = userMedicineRepo.save(userMedicine);

        logger.info("UserMedicine saved with id: {}", savedUserMedicine.getUserMedicineId());

        return mapToResponse(savedUserMedicine);
    }

    // Get all UserMedicines for a user
    public List<UserMedicineResponse> getUserMedicines(Long userId) {
        logger.info("Fetching UserMedicines for userId: {}", userId);

        List<UserMedicine> userMedicines = userMedicineRepo.findByUser_UserId(userId);

        List<UserMedicineResponse> responseList = userMedicines.stream().map(this::mapToResponse).collect(Collectors.toList());

        logger.info("Fetched {} UserMedicines for userId: {}", responseList.size(), userId);
        return responseList;
    }

    @Override
    public List<UserMedicineResponse> getUserMedicines(String username) {
        UserEntity user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("Fetching UserMedicines for login user : {}", username);
        return getUserMedicines(user.getUserId());
    }

    @Override
    public UserMedicineResponse getUserMedicineById(Long userMedicineId) {

        UserMedicine um = userMedicineRepo.findById(userMedicineId).orElseThrow(() -> new RuntimeException("User not found"));
        return mapToResponse(um);
    }

    @Override
    public void deleteUserMedicine(Long userMedicineId) {
        UserMedicine um = userMedicineRepo.findById(userMedicineId).orElseThrow(() -> new RuntimeException("User not found"));
        userMedicineRepo.delete(um);
    }

    @Override
    public void updateRemainingPills(Long userMedicineId, int newRemaining) {
        UserMedicine userMedicine = userMedicineRepo.findById(userMedicineId)
                .orElseThrow(() -> new RuntimeException("UserMedicine not found"));
        userMedicine.setRemainingPills(newRemaining);
        userMedicineRepo.save(userMedicine);
    }

    @Override
    public void takeMedicine(Long userMedicineId, int pillsTakenNow) {
        UserMedicine userMedicine = userMedicineRepo.findById(userMedicineId)
                .orElseThrow(() -> new RuntimeException("UserMedicine not found"));

        // Fallback in case values are null
        int existingPillsTaken = userMedicine.getPillsTaken() != null ? userMedicine.getPillsTaken() : 0;
        int existingRemaining = userMedicine.getRemainingPills() != null ? userMedicine.getRemainingPills() : 0;

        if (existingRemaining < pillsTakenNow) {
            throw new IllegalArgumentException("Not enough pills remaining.");
        }
        userMedicine.setPillsTaken(existingPillsTaken + pillsTakenNow);
        userMedicine.setRemainingPills(existingRemaining - pillsTakenNow);
        userMedicineRepo.save(userMedicine);
    }

    @Override
    public UserMedicineResponse updateUserMedicine(MedicineRequest medicineRequest, Long userMedicineId) {
        UserMedicine userMedicine = userMedicineRepo.findById(userMedicineId)
                .orElseThrow(() -> new RuntimeException("UserMedicine not found"));

        if (userMedicine.getMedicine() != null) {
            // Do not update medicine info if it's from master table
            //if you want to update it make new customMedicine
            userMedicine.setMedicine(null);

            CustomMedicine cm = new CustomMedicine();
            cm.setCustomMedicineName(medicineRequest.getMedicineName());
            cm.setPrice(medicineRequest.getPrice());
            cm.setManufacturer(medicineRequest.getManufacturer());
            cm.setAbout(medicineRequest.getAbout());
            cm.setComposition1(medicineRequest.getComposition1());
            cm.setComposition2(medicineRequest.getComposition2());
            cm.setPillsPerPack(medicineRequest.getPillsPerPack());
            cm.setCustomMedicineType(MedicineType.valueOf(medicineRequest.getMedicineType()));

            userMedicine.setCustomMedicine(cm);
        } else if (userMedicine.getCustomMedicine() != null) {
            CustomMedicine cm = userMedicine.getCustomMedicine();
            cm.setCustomMedicineName(medicineRequest.getMedicineName());
            cm.setManufacturer(medicineRequest.getManufacturer());
            cm.setComposition1(medicineRequest.getComposition1());
            cm.setComposition2(medicineRequest.getComposition2());
            cm.setPrice(medicineRequest.getPrice());
            cm.setCustomMedicineType(MedicineType.valueOf(medicineRequest.getMedicineType()));
            cm.setAbout(medicineRequest.getAbout());
            cm.setPillsPerPack(medicineRequest.getPillsPerPack());
        }

        UserMedicine updatedUserMedicine = userMedicineRepo.save(userMedicine);
        return mapToResponse(updatedUserMedicine);
    }

    private UserMedicineResponse mapToResponse(UserMedicine userMedicine) {

        if (userMedicine.getCustomMedicine() == null) {
            return UserMedicineResponse.builder()
                    .userMedicineId(userMedicine.getUserMedicineId())
                    .medicineId(userMedicine.getMedicine().getMedicineId())
                    .customMedicineId(null)
                    .medicineName(userMedicine.getMedicine().getMedicineName())
                    .manufacturer(userMedicine.getMedicine().getManufacturer())
                    .composition1(userMedicine.getMedicine().getComposition1())
                    .composition2(userMedicine.getMedicine().getComposition2())
                    .price(userMedicine.getMedicine().getPrice())
                    .medicineType(userMedicine.getMedicine().getMedicineType().name())
                    .about(userMedicine.getMedicine().getAbout())
                    .pillsPerPack(userMedicine.getMedicine().getPillsPerPack())
                    .quantityPacks(userMedicine.getQuantityPacks())
                    .addedDate(userMedicine.getAddedDate())
                    .remainingPills(userMedicine.getRemainingPills())
                    .pillsTaken(userMedicine.getPillsTaken())
                    .createdAt(userMedicine.getCreatedAt())
                    .updatedAt(userMedicine.getUpdatedAt())
                    .build();
        }

        return UserMedicineResponse.builder()
                .userMedicineId(userMedicine.getUserMedicineId())
                .medicineId(null)
                .customMedicineId(userMedicine.getCustomMedicine().getCustomMedicineId())
                .medicineName(userMedicine.getCustomMedicine().getCustomMedicineName())
                .manufacturer(userMedicine.getCustomMedicine().getManufacturer())
                .composition1(userMedicine.getCustomMedicine().getComposition1())
                .composition2(userMedicine.getCustomMedicine().getComposition2())
                .price(userMedicine.getCustomMedicine().getPrice())
                .medicineType(userMedicine.getCustomMedicine().getCustomMedicineType().name())
                .about(userMedicine.getCustomMedicine().getAbout())
                .pillsPerPack(userMedicine.getCustomMedicine().getPillsPerPack())
                .quantityPacks(userMedicine.getQuantityPacks())
                .addedDate(userMedicine.getAddedDate())
                .remainingPills(userMedicine.getRemainingPills())
                .pillsTaken(userMedicine.getPillsTaken())
                .createdAt(userMedicine.getCreatedAt())
                .updatedAt(userMedicine.getUpdatedAt())
                .build();
    }
}
