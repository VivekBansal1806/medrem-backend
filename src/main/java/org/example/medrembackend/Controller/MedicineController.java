package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;
import org.example.medrembackend.Service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping("/add")
    public ResponseEntity<MedicineResponse> addMedicine(@RequestBody MedicineRequest request) {
        MedicineResponse response = medicineService.addMedicine(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicineResponse>> getAllMedicines() {
        List<MedicineResponse> responseList = medicineService.getAllMedicines();
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable Long id) {
        MedicineResponse response = medicineService.getMedicineById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicineResponse>> searchMedicines(@RequestParam String name) {
        List<MedicineResponse> responseList = medicineService.searchMedicinesByName(name);
        return ResponseEntity.ok(responseList);
    }

}
