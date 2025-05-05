package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.ReminderRequest;
import org.example.medrembackend.DTOs.ReminderResponse;
import org.example.medrembackend.Entity.Reminder;
import org.example.medrembackend.Entity.UserMedicine;
import org.example.medrembackend.Repository.ReminderRepo;
import org.example.medrembackend.Repository.UserMedicineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    private final ReminderRepo reminderRepo;
    private final UserMedicineRepo userMedicineRepo;

    @Autowired
    public ReminderServiceImpl(ReminderRepo reminderRepo, UserMedicineRepo userMedicineRepo) {
        this.reminderRepo = reminderRepo;
        this.userMedicineRepo = userMedicineRepo;
    }

    @Override
    public ReminderResponse addReminder(ReminderRequest request) {
        Optional<UserMedicine> userMedicineOpt = userMedicineRepo.findById(request.getUserMedicineId());
        if (userMedicineOpt.isEmpty()) {
            throw new RuntimeException("UserMedicine not found with id: " + request.getUserMedicineId());
        }
        UserMedicine userMedicine = userMedicineOpt.get();

        Reminder reminder = new Reminder();
        reminder.setUserMedicine(userMedicine);
        reminder.setReminderTime(request.getReminderTime());
        reminder.setFrequency(request.getFrequency());
        reminder.setEnabled(request.isEnabled());

        Reminder saved = reminderRepo.save(reminder);

        return mapToResponse(saved);
    }

    @Override
    public List<ReminderResponse> getRemindersByUserMedicine(Long userMedicineId) {
        List<Reminder> reminders = reminderRepo.findByUserMedicine_UserMedicineId(userMedicineId);
        return reminders.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public ReminderResponse updateReminder(Long id, ReminderRequest request) {
        Optional<Reminder> reminderOpt = reminderRepo.findById(id);
        if (reminderOpt.isEmpty()) {
            throw new RuntimeException("Reminder not found with id: " + id);
        }
        Reminder reminder = reminderOpt.get();

        Optional<UserMedicine> userMedicineOpt = userMedicineRepo.findById(request.getUserMedicineId());
        if (userMedicineOpt.isEmpty()) {
            throw new RuntimeException("UserMedicine not found with id: " + request.getUserMedicineId());
        }
        reminder.setUserMedicine(userMedicineOpt.get());
        reminder.setReminderTime(request.getReminderTime());
        reminder.setFrequency(request.getFrequency());
        reminder.setEnabled(request.isEnabled());

        Reminder updated = reminderRepo.save(reminder);
        return mapToResponse(updated);
    }

    @Override
    public void deleteReminder(Long id) {
        reminderRepo.deleteById(id);
    }

    private ReminderResponse mapToResponse(Reminder reminder) {
        return ReminderResponse.builder()
                .reminderId(reminder.getReminderId())
                .userMedicineId(reminder.getUserMedicine().getUserMedicineId())
                .reminderTime(reminder.getReminderTime())
                .frequency(reminder.getFrequency())
                .enabled(reminder.isEnabled())
                .build();
    }
}
