package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.ReminderRequest;
import org.example.medrembackend.DTOs.ReminderResponse;

import java.util.List;

public interface ReminderService {
    ReminderResponse addReminder(ReminderRequest request);

    List<ReminderResponse> getRemindersByUserMedicine(Long userMedicineId);

    ReminderResponse updateReminder(Long id, ReminderRequest request);

    void deleteReminder(Long id);
}
