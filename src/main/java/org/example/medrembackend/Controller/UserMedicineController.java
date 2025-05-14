package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineResponse;
import org.example.medrembackend.Security.CustomUserDetails;
import org.example.medrembackend.Service.UserMedicineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-medicines")
public class UserMedicineController {
    private final UserMedicineService userMedicineService;

    Logger logger = LoggerFactory.getLogger(UserMedicineController.class);

    UserMedicineController(UserMedicineService userMedicineService) {
        this.userMedicineService = userMedicineService;
    }

    @PostMapping("/add/{medicineId}")
    public ResponseEntity<UserMedicineResponse> adduserMedicine(@PathVariable Long medicineId, @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getUserId();
        logger.info("Adding medicine from master table. userId: {}, medicineId: {}", userId, medicineId);

        UserMedicineResponse response = userMedicineService.addMedicineToUserMedicine(userId, medicineId);

        logger.info("Successfully added master medicine. userMedicineId: {}", response.getUserMedicineId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-new")
    public ResponseEntity<UserMedicineResponse> addNewUserMedicine(@RequestBody MedicineRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {

        long userId = userDetails.getUserId();
        logger.info("Adding new custom medicine for userId: {} with request: {}", userId, request);

        UserMedicineResponse response = userMedicineService.createNewUserMedicine(request, userId);

        logger.info("Successfully added new custom medicine. userMedicineId: {}", response.getUserMedicineId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllUserMedicines")
    public ResponseEntity<List<UserMedicineResponse>> getAllUserMedicines(@AuthenticationPrincipal CustomUserDetails userDetails) {

        String username = userDetails.getUsername();
        logger.info("Fetching all medicines for user: {}", username);

        List<UserMedicineResponse> responses = userMedicineService.getUserMedicines(username);

        logger.info("Fetched {} medicines for user: {}", responses.size(), username);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/user-medicine-id/{userMedicineId}")
    public ResponseEntity<UserMedicineResponse> getSingleUserMedicine(@PathVariable Long userMedicineId) {
        logger.info("Fetching UserMedicine with ID: {}", userMedicineId);

        UserMedicineResponse response = userMedicineService.getUserMedicineById(userMedicineId);

        logger.info("Successfully fetched UserMedicine: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/take/{userMedicineId}")
    public ResponseEntity<String> takeMedicine(@PathVariable Long userMedicineId, @RequestParam int pillsTaken) {

        logger.info("Marking {} pills as taken for userMedicineId: {}", pillsTaken, userMedicineId);

        userMedicineService.takeMedicine(userMedicineId, pillsTaken);

        logger.info("Pills marked as taken and inventory updated for userMedicineId: {}", userMedicineId);
        return ResponseEntity.ok("Pills taken successfully. Inventory updated.");
    }

    @PutMapping("/updateQuantity/{userMedicineId}")
    public ResponseEntity<String> updateQuantity(@PathVariable Long userMedicineId, @RequestParam int newQuantity) {

        logger.info("Updating remaining pills to {} for userMedicineId: {}", newQuantity, userMedicineId);

        userMedicineService.updateRemainingPills(userMedicineId, newQuantity);

        logger.info("Quantity updated successfully for userMedicineId: {}", userMedicineId);
        return ResponseEntity.ok("Quantity updated successfully.");
    }

    @PutMapping("/update-User-medicine/{userMedicineId}")
    public ResponseEntity<UserMedicineResponse> updateUserMedicine(@RequestBody MedicineRequest medicineRequest, @PathVariable Long userMedicineId) {

        logger.info("Updating user medicine with ID: {} using request: {}", userMedicineId, medicineRequest);

        UserMedicineResponse response = userMedicineService.updateUserMedicine(medicineRequest, userMedicineId);

        logger.info("User medicine updated successfully. userMedicineId: {}", response.getUserMedicineId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{userMedicineId}")
    public ResponseEntity<String> deleteUserMedicine(@PathVariable Long userMedicineId) {
        logger.info("Deleting user medicine with ID: {}", userMedicineId);

        userMedicineService.deleteUserMedicine(userMedicineId);

        logger.info("User medicine deleted successfully. userMedicineId: {}", userMedicineId);
        return ResponseEntity.ok("User medicine deleted successfully.");
    }

}
