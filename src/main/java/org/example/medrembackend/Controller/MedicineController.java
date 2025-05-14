package org.example.medrembackend.Controller;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;
import org.example.medrembackend.Service.MedicineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final Logger logger = LoggerFactory.getLogger(MedicineController.class);

    private final MedicineService medicineService;

    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @PostMapping("/add")
    public ResponseEntity<MedicineResponse> addMedicine(@RequestBody MedicineRequest request) {
        logger.info("Adding medicine with name: {}", request.getMedicineName());
        MedicineResponse response = medicineService.addMedicine(request);
        logger.info("Medicine added with id: {}", response.getMedicineId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MedicineResponse>> getAllMedicines() {
        logger.info("Fetching all medicines");
        List<MedicineResponse> responseList = medicineService.getAllMedicines();
        logger.info("Fetched {} medicines", responseList.size());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/get/{medId}")
    public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable Long medId) {
        logger.info("Fetching medicine with medId: {}", medId);
        MedicineResponse response = medicineService.getMedicineByMedicineId(medId);
        logger.info("Fetched medicine: {}", response.getMedicineName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MedicineResponse>> searchMedicines(@RequestParam String name) {
        logger.info("Searching medicines with name containing: {}", name);
        List<MedicineResponse> responseList = medicineService.searchMedicinesByMedicineName(name);
        logger.info("Found {} medicines matching search", responseList.size());
        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/delete/{medId}")
    public ResponseEntity<MedicineResponse> deleteMedicine(@PathVariable Long medId) {
        logger.info("Deleting medicine with medId: {}", medId);
        MedicineResponse medRes=medicineService.deleteMedicineByMedicineId(medId);
        logger.info("Deleted medicine with medId: {}", medId);
        return ResponseEntity.ok(medRes);
    }

    @PutMapping("/update/{medId}")
    public ResponseEntity<MedicineResponse> updateMedicine(@PathVariable Long medId, @RequestBody MedicineRequest request) {
        logger.info("Updating medicine with medId: {}", medId);
        MedicineResponse response = medicineService.updateMedicine(medId, request);
        logger.info("Updated medicine with id: {}", medId);
        return ResponseEntity.ok(response);}

}
