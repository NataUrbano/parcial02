package com.library.parcial02.repository;

import com.library.parcial02.model.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface VehicleTypeRepository extends JpaRepository<VehicleTypeEntity, Long> {

    Optional<VehicleTypeEntity> findByName(String name);
}
