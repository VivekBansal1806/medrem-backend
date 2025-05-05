package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.UserMedicineRequest;
import org.example.medrembackend.DTOs.UserMedicineResponse;
import org.example.medrembackend.Service.UserMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-medicines")
public class UserMedicineController {

    private final UserMedicineService userMedicineService;

    @Autowired
    UserMedicineController(UserMedicineService userMedicineService) {
        this.userMedicineService = userMedicineService;
    }

    // Endpoint to add a new UserMedicine
    @PostMapping("/add/{userId}/{medicineId}")
    public ResponseEntity<UserMedicineResponse> addUserMedicine(@RequestBody UserMedicineRequest request, @PathVariable Long userId, @PathVariable Long medicineId) {
        UserMedicineResponse response = userMedicineService.addUserMedicine(request, userId, medicineId);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get all UserMedicines for a user
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserMedicineResponse>> getUserMedicines(@PathVariable Long userId) {
        List<UserMedicineResponse> response = userMedicineService.getUserMedicines(userId);
        return ResponseEntity.ok(response);
    }

    // Endpoint to get a specific UserMedicine
    @GetMapping("/{userId}/{medicineId}")
    public ResponseEntity<UserMedicineResponse> getUserMedicine(@PathVariable Long userId, @PathVariable Long medicineId) {
        UserMedicineResponse response = userMedicineService.getUserMedicine(userId, medicineId);
        return ResponseEntity.ok(response);
    }
}
