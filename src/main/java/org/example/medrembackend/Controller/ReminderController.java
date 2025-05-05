package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.ReminderRequest;
import org.example.medrembackend.DTOs.ReminderResponse;
import org.example.medrembackend.Service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reminders")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping("/")
    public ResponseEntity<ReminderResponse> addReminder(@RequestBody ReminderRequest request) {
        ReminderResponse response = reminderService.addReminder(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user-medicine/{userMedicineId}")
    public ResponseEntity<List<ReminderResponse>> getRemindersByUserMedicine(@PathVariable Long userMedicineId) {
        List<ReminderResponse> responseList = reminderService.getRemindersByUserMedicine(userMedicineId);
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponse> updateReminder(@PathVariable Long id, @RequestBody ReminderRequest request) {
        ReminderResponse response = reminderService.updateReminder(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.noContent().build();
    }
}
