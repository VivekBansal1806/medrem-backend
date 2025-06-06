package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;
import org.example.medrembackend.Entity.Medicine;
import org.example.medrembackend.Entity.MedicineType;
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
        logger.info("Adding medicine with name: {}", request.getMedicineName());

        // Create a new Medicine entity
        Medicine medicine = new Medicine();
        medicine.setMedicineName(request.getMedicineName());
        medicine.setPillsPerPack(request.getPillsPerPack());
        medicine.setPrice(request.getPrice());
        medicine.setManufacturer(request.getManufacturer());
        medicine.setMedicineType(MedicineType.valueOf(request.getMedicineType().toUpperCase()));
        medicine.setComposition1(request.getComposition1());
        medicine.setComposition2(request.getComposition2());
        medicine.setAbout(request.getAbout());

        // Save the Medicine entity to the database
        Medicine saved = medicineRepo.save(medicine);
        logger.info("Medicine saved with id: {}", saved.getMedicineId());

        // Return the response, converting the medicineType Enum to String for the response
        return mapToResponse(saved);
    }


    @Override
    public List<MedicineResponse> getAllMedicines() {
        logger.info("Fetching all medicines");
        List<MedicineResponse> responseList = medicineRepo.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
        logger.info("Fetched {} medicines", responseList.size());
        return responseList;
    }

    @Override
    public MedicineResponse getMedicineByMedicineId(Long id) {
        logger.info("Fetching medicine with id: {}", id);
        Medicine medicine = medicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        logger.info("Fetched medicine: {}", medicine.getMedicineName());

        return mapToResponse(medicine);
    }

    @Override
    public List<MedicineResponse> searchMedicinesByMedicineName(String name) {
        logger.info("Searching medicines with name containing: {}", name);
        List<Medicine> medicines = medicineRepo.findByMedicineNameContainingIgnoreCase(name);
        List<MedicineResponse> responseList = medicines.stream().map(this::mapToResponse).collect(Collectors.toList());
        logger.info("Found {} medicines matching search", responseList.size());
        return responseList;
    }

    @Override
    public MedicineResponse updateMedicine(long id, MedicineRequest request) {
        Medicine med = medicineRepo.findById(id).orElseThrow(() -> new RuntimeException("Medicine not found"));

        med.setMedicineName(request.getMedicineName());
        med.setPillsPerPack(request.getPillsPerPack());
        med.setPrice(request.getPrice());
        med.setManufacturer(request.getManufacturer());
        med.setMedicineType(MedicineType.valueOf(request.getMedicineType()));
        med.setComposition1(request.getComposition1());
        med.setComposition2(request.getComposition2());
        med.setAbout(request.getAbout());
        Medicine savedMedicine = medicineRepo.save(med);

        return mapToResponse(savedMedicine);
    }

    @Override
    public MedicineResponse deleteMedicineByMedicineId(Long id) {
        Medicine medicine = medicineRepo.findById(id).orElseThrow(() -> new RuntimeException("Medicine not found"));
        medicineRepo.delete(medicine);
        return mapToResponse(medicine);
    }

    @Override
    public MedicineResponse deleteMedicineByMedicineName(String medicineName) {

        Medicine med = medicineRepo.findMedicinesByMedicineName(medicineName);
        medicineRepo.delete(med);
        return mapToResponse(med);
    }

    private MedicineResponse mapToResponse(Medicine medicine) {

        return MedicineResponse.builder()
                .medicineId(medicine.getMedicineId())
                .medicineName(medicine.getMedicineName())
                .pillsPerPack(medicine.getPillsPerPack())
                .price(medicine.getPrice())
                .manufacturer(medicine.getManufacturer())
                .medicineType(medicine.getMedicineType().name())
                .composition1(medicine.getComposition1())
                .composition2(medicine.getComposition2())
                .about(medicine.getAbout())
                .build();
    }
}
