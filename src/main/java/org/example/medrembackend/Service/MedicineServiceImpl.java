package org.example.medrembackend.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;
import org.example.medrembackend.Entity.Medicine;
import org.example.medrembackend.Repository.MedicineRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setPackSize(request.getPackSize());
        medicine.setPrice(request.getPrice());
        medicine.setManufacturer(request.getManufacturer());
        medicine.setType(request.getType());
        medicine.setComposition1(request.getComposition1());
        medicine.setComposition2(request.getComposition2());

        Medicine saved = medicineRepo.save(medicine);
        logger.info("Medicine saved with id: {}", saved.getMedicineId());

        return MedicineResponse.builder()
                .medicineId(saved.getMedicineId())
                .name(saved.getName())
                .packSize(saved.getPackSize())
                .price(saved.getPrice())
                .manufacturer(saved.getManufacturer())
                .type(saved.getType())
                .composition1(saved.getComposition1())
                .composition2(saved.getComposition2())
                .build();
    }

    @Override
    public List<MedicineResponse> getAllMedicines() {
        logger.info("Fetching all medicines");
        List<MedicineResponse> responseList = medicineRepo.findAll().stream().map(medicine ->
                MedicineResponse.builder()
                        .medicineId(medicine.getMedicineId())
                        .name(medicine.getName())
                        .packSize(medicine.getPackSize())
                        .price(medicine.getPrice())
                        .manufacturer(medicine.getManufacturer())
                        .type(medicine.getType())
                        .composition1(medicine.getComposition1())
                        .composition2(medicine.getComposition2())
                        .build()
        ).collect(Collectors.toList());
        logger.info("Fetched {} medicines", responseList.size());
        return responseList;
    }

    @Override
    public MedicineResponse getMedicineById(Long id) {
        logger.info("Fetching medicine with id: {}", id);
        Medicine medicine = medicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));
        logger.info("Fetched medicine: {}", medicine.getName());

        return MedicineResponse.builder()
                .medicineId(medicine.getMedicineId())
                .name(medicine.getName())
                .packSize(medicine.getPackSize())
                .price(medicine.getPrice())
                .manufacturer(medicine.getManufacturer())
                .type(medicine.getType())
                .composition1(medicine.getComposition1())
                .composition2(medicine.getComposition2())
                .build();
    }

    @Override
    public List<MedicineResponse> searchMedicinesByName(String name) {
        logger.info("Searching medicines with name containing: {}", name);
        List<Medicine> medicines = medicineRepo.findByNameContainingIgnoreCase(name);
        List<MedicineResponse> responseList = medicines.stream().map(medicine ->
                MedicineResponse.builder()
                        .medicineId(medicine.getMedicineId())
                        .name(medicine.getName())
                        .packSize(medicine.getPackSize())
                        .price(medicine.getPrice())
                        .manufacturer(medicine.getManufacturer())
                        .type(medicine.getType())
                        .composition1(medicine.getComposition1())
                        .composition2(medicine.getComposition2())
                        .build()
        ).collect(Collectors.toList());
        logger.info("Found {} medicines matching search", responseList.size());
        return responseList;
    }

}
