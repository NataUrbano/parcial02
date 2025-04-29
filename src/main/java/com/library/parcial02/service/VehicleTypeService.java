package com.library.parcial02.service;

import com.library.parcial02.model.VehicleTypeEntity;
import com.library.parcial02.repository.VehicleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service

public class VehicleTypeService {

    @Autowired
    private VehicleTypeRepository vehicleTypeRepository;

    @Transactional(readOnly = true)
    public List<VehicleTypeEntity> findAll() {
        return vehicleTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<VehicleTypeEntity> findById(Long id) {
        return vehicleTypeRepository.findById(id);
    }

    @Transactional
    public VehicleTypeEntity save(VehicleTypeEntity vehicleType) {
        return vehicleTypeRepository.save(vehicleType);
    }

    @Transactional
    public void deleteById(Long id) {
        vehicleTypeRepository.deleteById(id);
    }
}
