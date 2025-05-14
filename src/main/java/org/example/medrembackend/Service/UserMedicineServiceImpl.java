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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    @Deprecated
    public UserMedicineResponse addUserMedicine(UserMedicineRequest request, long userId, Long medicineId) {
        logger.info("Adding UserMedicine for username: {}", userId);
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        logger.info("Adding UserMedicine for username: {} and medicineId: {}", user.getUsername(), medicineId);

        Optional<Medicine> medicine = medicineRepo.findById(medicineId);
        UserMedicine userMedicine = new UserMedicine();

        userMedicine.setUser(user);
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

    public UserMedicineResponse addMedicineToUserMedicine(UserMedicineRequest request, long userId, Long medicineId) {
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
        userMedicine.setQuantityPacks(request.getQuantityPacks());
        userMedicine.setAddedDate(LocalDate.now());

        UserMedicine savedUserMedicine = userMedicineRepo.save(userMedicine);
        logger.info("UserMedicine saved with id: {}", savedUserMedicine.getUserMedicineId());

        return UserMedicineResponse.builder()
                .userMedicineId(savedUserMedicine.getUserMedicineId())
                .medicineId(savedUserMedicine.getMedicine().getMedicineId())
                .medicineName(savedUserMedicine.getMedicine().getMedicineName())
                .manufacturer(savedUserMedicine.getMedicine().getManufacturer())
                .composition1(savedUserMedicine.getMedicine().getComposition1())
                .composition2(savedUserMedicine.getMedicine().getComposition2())
                .price(savedUserMedicine.getMedicine().getPrice())
                .medicineType(savedUserMedicine.getMedicine().getMedicineType().name())
                .about(savedUserMedicine.getMedicine().getAbout())
                .quantityPacks(savedUserMedicine.getQuantityPacks())
                .pillsPerPack(savedUserMedicine.getMedicine().getPillsPerPack())
                .addedDate(savedUserMedicine.getAddedDate())
                .build();
    }

    public UserMedicineResponse createNewUserMedicine(UserMedicineRequest request, long userId) {
        logger.info("Creating new UserMedicine for userId: {}", userId);
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        UserMedicine userMedicine = new UserMedicine();
        userMedicine.setUser(user);
        userMedicine.setMedicine(null);
        userMedicine.setAddedDate(LocalDate.now());

        userMedicine.setPillsPerPack(request.getPillsPerPack());
        userMedicine.setQuantityPacks(request.getQuantityPacks());
        userMedicine.setMedicineName(request.getMedicineName());
        userMedicine.setManufacturer(request.getManufacturer());
        userMedicine.setComposition1(request.getComposition1());
        userMedicine.setComposition2(request.getComposition2());
        userMedicine.setPrice(request.getPrice());
        userMedicine.setType(request.getMedicineType());
        userMedicine.setAbout(request.getAbout());

        UserMedicine savedUserMedicine = userMedicineRepo.save(userMedicine);
        logger.info("UserMedicine saved with id: {}", savedUserMedicine.getUserMedicineId());

        return UserMedicineResponse.builder()
                .userMedicineId(savedUserMedicine.getUserMedicineId())
                .medicineId(null)
                .medicineName(savedUserMedicine.getMedicineName())
                .manufacturer(savedUserMedicine.getManufacturer())
                .composition1(savedUserMedicine.getComposition1())
                .composition2(savedUserMedicine.getComposition2())
                .price(savedUserMedicine.getPrice())
                .medicineType(savedUserMedicine.getType())
                .about(savedUserMedicine.getAbout())
                .quantityPacks(savedUserMedicine.getQuantityPacks())
                .pillsPerPack(savedUserMedicine.getPillsPerPack())
                .addedDate(savedUserMedicine.getAddedDate())
                .build();
    }

    // Get all UserMedicines for a user
    public List<UserMedicineResponse> getUserMedicines(Long userId) {
        logger.info("Fetching UserMedicines for userId: {}", userId);

        List<UserMedicine> userMedicines = userMedicineRepo.findByUser_UserId(userId);

        List<UserMedicineResponse> responseList = userMedicines.stream().map(userMedicine -> {
            UserMedicineResponse.UserMedicineResponseBuilder responseBuilder = UserMedicineResponse.builder()
                    .userMedicineId(userMedicine.getUserMedicineId())
                    .quantityPacks(userMedicine.getQuantityPacks())
                    .addedDate(userMedicine.getAddedDate());

            // Check if medicine exists, then populate the response accordingly
            if (userMedicine.getMedicine() != null) {
                responseBuilder
                        .medicineId(userMedicine.getMedicine().getMedicineId())
                        .medicineName(userMedicine.getMedicine().getMedicineName())
                        .manufacturer(userMedicine.getMedicine().getManufacturer())
                        .composition1(userMedicine.getMedicine().getComposition1())
                        .composition2(userMedicine.getMedicine().getComposition2())
                        .price(userMedicine.getMedicine().getPrice())
                        .pillsPerPack(userMedicine.getMedicine().getPillsPerPack())
                        .medicineType(userMedicine.getMedicine().getMedicineType().name())
                        .about(userMedicine.getMedicine().getAbout());
            } else {
                // If medicine is null, use custom fields from UserMedicine if available
                responseBuilder
                        .medicineId(null)
                        .medicineName(userMedicine.getMedicineName())  // Custom Name if Medicine is not found
                        .manufacturer(userMedicine.getManufacturer())  // Custom Manufacturer
                        .composition1(userMedicine.getComposition1())  // Custom Composition1
                        .composition2(userMedicine.getComposition2())  // Custom Composition2
                        .price(userMedicine.getPrice())                // Custom Price
                        .medicineType(userMedicine.getType())          // Custom Medicine Type
                        .about(userMedicine.getAbout())                // Custom About
                        .pillsPerPack(userMedicine.getPillsPerPack()); // custom pills per pack
            }

            return responseBuilder.build();
        }).toList();

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

        UserMedicine um=userMedicineRepo.findById(userMedicineId).orElseThrow(() -> new RuntimeException("User not found"));

        if(um.getMedicine()==null){
            return UserMedicineResponse.builder()
                    .userMedicineId(userMedicineId)
                    .medicineId(null)
                    .medicineName(um.getMedicineName())
                    .manufacturer(um.getManufacturer())
                    .composition1(um.getComposition1())
                    .composition2(um.getComposition2())
                    .price(um.getPrice())
                    .pillsPerPack(um.getPillsPerPack())
                    .quantityPacks(um.getQuantityPacks())
                    .addedDate(um.getAddedDate())
                    .about(um.getAbout())
                    .build();
        }
        return UserMedicineResponse.builder()
                .userMedicineId(userMedicineId)
                .medicineName(um.getMedicine().getMedicineName())
                .manufacturer(um.getMedicine().getManufacturer())
                .composition1(um.getMedicine().getComposition1())
                .composition2(um.getMedicine().getComposition2())
                .price(um.getMedicine().getPrice())
                .pillsPerPack(um.getMedicine().getPillsPerPack())
                .quantityPacks(um.getQuantityPacks())
                .addedDate(um.getAddedDate())
                .about(um.getMedicine().getAbout())
                .medicineType(um.getMedicine().getMedicineType().name())
                .build();

    }

    @Override
    public void deleteUserMedicine(Long userMedicineId) {
        UserMedicine um=userMedicineRepo.findById(userMedicineId).orElseThrow(() -> new RuntimeException("User not found"));
        userMedicineRepo.delete(um);
        return;
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
    public UserMedicineResponse updateUsermedicine(UserMedicineRequest userMedicineRequest, Long userMedicineId) {
        UserMedicine userMedicine = userMedicineRepo.findById(userMedicineId)
                .orElseThrow(() -> new RuntimeException("UserMedicine not found"));

        userMedicine.setMedicineName(userMedicineRequest.getMedicineName());
        userMedicine.setManufacturer(userMedicineRequest.getManufacturer());
        userMedicine.setComposition1(userMedicineRequest.getComposition1());
        userMedicine.setComposition2(userMedicineRequest.getComposition2());
        userMedicine.setPrice(userMedicineRequest.getPrice());
        userMedicine.setType(userMedicineRequest.getMedicineType());
        userMedicine.setAbout(userMedicineRequest.getAbout());
        userMedicine.setQuantityPacks(userMedicineRequest.getQuantityPacks());
        userMedicine.setPillsPerPack(userMedicineRequest.getPillsPerPack());
        userMedicine.setAddedDate(userMedicineRequest.getAddedDate());
        userMedicine.setRemainingPills(userMedicineRequest.getRemainingPills());
        userMedicine.setPillsTaken(userMedicineRequest.getPillsTaken());

        UserMedicine updatedUserMedicine = userMedicineRepo.save(userMedicine);

        return UserMedicineResponse.builder()
                .userMedicineId(updatedUserMedicine.getUserMedicineId())
                .medicineId(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getMedicineId() : null)
                .medicineName(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getMedicineName() : updatedUserMedicine.getMedicineName())
                .manufacturer(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getManufacturer() : updatedUserMedicine.getManufacturer())
                .composition1(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getComposition1() : updatedUserMedicine.getComposition1())
                .composition2(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getComposition2() : updatedUserMedicine.getComposition2())
                .price(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getPrice() : updatedUserMedicine.getPrice())
                .medicineType(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getMedicineType().name() : updatedUserMedicine.getType())
                .about(updatedUserMedicine.getMedicine() != null ? updatedUserMedicine.getMedicine().getAbout() : updatedUserMedicine.getAbout())
                .quantityPacks(updatedUserMedicine.getQuantityPacks())
                .pillsPerPack(updatedUserMedicine.getPillsPerPack())
                .addedDate(updatedUserMedicine.getAddedDate())
                .remainingPills(updatedUserMedicine.getRemainingPills())
                .pillsTaken(updatedUserMedicine.getPillsTaken())
                .createdAt(updatedUserMedicine.getCreatedAt())
                .updatedAt(updatedUserMedicine.getUpdatedAt())
                .build();
    }
}
