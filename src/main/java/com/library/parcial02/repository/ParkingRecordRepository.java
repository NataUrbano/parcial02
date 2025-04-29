package com.library.parcial02.repository;

import com.library.parcial02.model.ParkingRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ParkingRecordRepository extends JpaRepository<ParkingRecordEntity, Long> {

    List<ParkingRecordRepository> findByLicensePlate(String licensePlate);

    @Query("SELECT p FROM ParkingRecordEntity p WHERE p.exitTime IS NULL")
    List<ParkingRecordEntity> findAllActiveParking();

    @Query("SELECT p FROM ParkingRecordEntity p WHERE p.licensePlate = :licensePlate AND p.exitTime IS NULL")
    Optional<ParkingRecordEntity> findActiveByLicensePlate(String licensePlate);

    List<ParkingRecordRepository> findByLocation(String location);
}
