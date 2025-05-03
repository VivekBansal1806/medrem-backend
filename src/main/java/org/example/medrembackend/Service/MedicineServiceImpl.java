package org.example.medrembackend.Service;

import org.example.medrembackend.DTOs.MedicineRequest;
import org.example.medrembackend.DTOs.MedicineResponse;
import org.example.medrembackend.Entity.Medicine;
import org.example.medrembackend.Repository.MedicineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepo medicineRepo;

    @Autowired
    public MedicineServiceImpl(MedicineRepo medicineRepo) {
        this.medicineRepo = medicineRepo;
    }

    @Override
    public MedicineResponse addMedicine(MedicineRequest request) {
        Medicine medicine = new Medicine();
        medicine.setName(request.getName());
        medicine.setPackSize(request.getPackSize());
        medicine.setPrice(request.getPrice());
        medicine.setManufacturer(request.getManufacturer());
        medicine.setType(request.getType());
        medicine.setComposition1(request.getComposition1());
        medicine.setComposition2(request.getComposition2());

        Medicine saved = medicineRepo.save(medicine);

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
        return medicineRepo.findAll().stream().map(medicine ->
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
    }

    @Override
    public MedicineResponse getMedicineById(Long id) {
        Medicine medicine = medicineRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Medicine not found"));

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
        List<Medicine> medicines = medicineRepo.findByNameContainingIgnoreCase(name);
        return medicines.stream().map(medicine ->
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
    }

}
