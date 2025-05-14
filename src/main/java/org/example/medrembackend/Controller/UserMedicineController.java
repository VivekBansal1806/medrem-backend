package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.UserMedicineRequest;
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
    public ResponseEntity<UserMedicineResponse> adduserMedicine(@RequestBody UserMedicineRequest medicineRequest, @PathVariable Long medicineId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUserId();
        UserMedicineResponse response = userMedicineService.addMedicineToUserMedicine(medicineRequest, userId, medicineId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-new")
    public ResponseEntity<UserMedicineResponse> addNewUserMedicine(@RequestBody UserMedicineRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUserId();
        UserMedicineResponse response = userMedicineService.createNewUserMedicine(request, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllUserMedicines")
    public ResponseEntity<List<UserMedicineResponse>> getAllUserMedicines(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String username = userDetails.getUsername();
        List<UserMedicineResponse> responses = userMedicineService.getUserMedicines(username);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/get/{userMedicineId}")
    public ResponseEntity<UserMedicineResponse> getSingleUserMedicine(@PathVariable Long userMedicineId) {
        logger.info("getSingleUserMedicine");
        return ResponseEntity.ok(userMedicineService.getUserMedicineById(userMedicineId));
    }

    // 1. Mark a pill as taken
    @PostMapping("/take/{userMedicineId}")
    public ResponseEntity<String> takeMedicine(@PathVariable Long userMedicineId, @RequestParam int pillsTaken) {
        userMedicineService.takeMedicine(userMedicineId, pillsTaken);
        return ResponseEntity.ok("Pills taken successfully. Inventory updated.");
    }

    // 2. Update quantity manually (e.g. refill)
    @PutMapping("/updateQuantity/{userMedicineId}")
    public ResponseEntity<String> updateQuantity(
            @PathVariable Long userMedicineId,
            @RequestParam int newQuantity) {

        userMedicineService.updateRemainingPills(userMedicineId, newQuantity);
        return ResponseEntity.ok("Quantity updated successfully.");
    }

    @PutMapping("/update-User-medicine/{userMedicineId}")
    public ResponseEntity<UserMedicineResponse> updateUserMedicine(@RequestBody UserMedicineRequest userMedicineRequest, @PathVariable Long userMedicineId) {

        UserMedicineResponse response=userMedicineService.updateUsermedicine(userMedicineRequest,userMedicineId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{userMedicineId}")
    public ResponseEntity<String> deleteUserMedicine(@PathVariable Long userMedicineId) {
        userMedicineService.deleteUserMedicine(userMedicineId);
        return ResponseEntity.ok("User medicine deleted successfully.");
    }

}
