package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;
import org.example.medrembackend.Service.MedicineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final Logger logger = LoggerFactory.getLogger(MedicineController.class);

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping("/add")
    public ResponseEntity<MedicineResponse> addMedicine(@RequestBody MedicineRequest request) {
        logger.info("Adding medicine with name: {}", request.getName());
        MedicineResponse response = medicineService.addMedicine(request);
        logger.info("Medicine added with id: {}", response.getMedicineId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicineResponse>> getAllMedicines() {
        logger.info("Fetching all medicines");
        List<MedicineResponse> responseList = medicineService.getAllMedicines();
        logger.info("Fetched {} medicines", responseList.size());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable Long id) {
        logger.info("Fetching medicine with id: {}", id);
        MedicineResponse response = medicineService.getMedicineByMedicineId(id);
        logger.info("Fetched medicine: {}", response.getName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicineResponse>> searchMedicines(@RequestParam String name) {
        logger.info("Searching medicines with name containing: {}", name);
        List<MedicineResponse> responseList = medicineService.searchMedicinesByMedicineName(name);
        logger.info("Found {} medicines matching search", responseList.size());
        return ResponseEntity.ok(responseList);
    }

}
