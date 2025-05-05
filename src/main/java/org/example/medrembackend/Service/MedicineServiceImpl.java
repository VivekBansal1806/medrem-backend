package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;
import org.example.medrembackend.Entity.Medicine;
import org.example.medrembackend.Repository.MedicineRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final Logger logger = LoggerFactory.getLogger(MedicineServiceImpl.class);

    private final MedicineRepo medicineRepo;

    @Autowired
    public MedicineServiceImpl(MedicineRepo medicineRepo) {
        this.medicineRepo = medicineRepo;
    }

    @Override
    public MedicineResponse addMedicine(MedicineRequest request) {
        logger.info("Adding medicine with name: {}", request.getName());

        // Create a new Medicine entity
        Medicine medicine = new Medicine();
        medicine.setMedicineName(request.getName());
        medicine.setPillsPerPack(request.getPillsPerPack());
        medicine.setPrice(request.getPrice());
        medicine.setManufacturer(request.getManufacturer());
        medicine.setMedicineType(Medicine.MedicineType.valueOf(request.getMedicineType().toUpperCase()));
        medicine.setComposition1(request.getComposition1());
        medicine.setComposition2(request.getComposition2());
        medicine.setAbout(request.getAbout());

        // Save the Medicine entity to the database
        Medicine saved = medicineRepo.save(medicine);
        logger.info("Medicine saved with id: {}", saved.getMedicineId());

        // Return the response, converting the medicineType Enum to String for the response
        return MedicineResponse.builder()
                .medicineId(saved.getMedicineId())
                .name(saved.getMedicineName())
                .pillsPerPack(saved.getPillsPerPack())
                .price(saved.getPrice())
                .manufacturer(saved.getManufacturer())
                .medicineType(saved.getMedicineType().name())  // Convert Enum to String for response
                .composition1(saved.getComposition1())
                .composition2(saved.getComposition2())
                .about(saved.getAbout())
                .build();
    }


    @Override
    public List<MedicineResponse> getAllMedicines() {
        logger.info("Fetching all medicines");
        List<MedicineResponse> responseList = medicineRepo.findAll().stream().map(medicine ->
                MedicineResponse.builder()
                        .medicineId(medicine.getMedicineId())
                        .name(medicine.getMedicineName())
                        .pillsPerPack(medicine.getPillsPerPack())
                        .price(medicine.getPrice())
                        .manufacturer(medicine.getManufacturer())
                        .medicineType(medicine.getMedicineType().name())
                        .composition1(medicine.getComposition1())
                        .composition2(medicine.getComposition2())
                        .about(medicine.getAbout())
                        .build()
        ).collect(Collectors.toList());
        logger.info("Fetched {} medicines", responseList.size());
        return responseList;
    }

    @Override
    public MedicineResponse getMedicineByMedicineId(Long id) {
        logger.info("Fetching medicine with id: {}", id);
        Medicine medicine = medicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        logger.info("Fetched medicine: {}", medicine.getMedicineName());

        return MedicineResponse.builder()
                .medicineId(medicine.getMedicineId())
                .name(medicine.getMedicineName())
                .pillsPerPack(medicine.getPillsPerPack())
                .price(medicine.getPrice())
                .manufacturer(medicine.getManufacturer())
                .medicineType(medicine.getMedicineType().name())
                .composition1(medicine.getComposition1())
                .composition2(medicine.getComposition2())
                .about(medicine.getAbout())
                .build();
    }

    @Override
    public List<MedicineResponse> searchMedicinesByMedicineName(String name) {
        logger.info("Searching medicines with name containing: {}", name);
        List<Medicine> medicines = medicineRepo.findByMedicineNameContainingIgnoreCase(name);
        List<MedicineResponse> responseList = medicines.stream().map(medicine ->
                MedicineResponse.builder()
                        .medicineId(medicine.getMedicineId())
                        .name(medicine.getMedicineName())
                        .pillsPerPack(medicine.getPillsPerPack())
                        .price(medicine.getPrice())
                        .manufacturer(medicine.getManufacturer())
                        .medicineType(medicine.getMedicineType().name())
                        .composition1(medicine.getComposition1())
                        .composition2(medicine.getComposition2())
                        .about(medicine.getAbout())
                        .build()
        ).collect(Collectors.toList());
        logger.info("Found {} medicines matching search", responseList.size());
        return responseList;
    }

}
