package com.library.parcial02.service;

import com.library.parcial02.model.ParkingRecordEntity;
import com.library.parcial02.model.UserEntity;
import com.library.parcial02.repository.ParkingRecordRepository;
import com.library.parcial02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class ParkingRecordService {

    @Autowired
    private ParkingRecordRepository parkingRecordRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ParkingRecordEntity> findAll() {
        return parkingRecordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<ParkingRecordEntity> findAllActive() {
        return parkingRecordRepository.findAllActiveParking();
    }

    @Transactional(readOnly = true)
    public Optional<ParkingRecordEntity> findById(Long id) {
        return parkingRecordRepository.findById(id);
    }

    @Transactional
    public ParkingRecordEntity save(ParkingRecordEntity record, String username) {
        if (record.getId() == null) {
            record.setEntryTime(LocalDateTime.now());

            UserEntity user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            record.setRegisteredBy(user);
        } else {
            UserEntity updatedBy = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            record.setUpdatedBy(updatedBy);
        }

        return parkingRecordRepository.save(record);
    }

    @Transactional
    public ParkingRecordEntity registerExit(Long id, String username) {
        ParkingRecordEntity record = parkingRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parking record not found"));

        if (record.getExitTime() != null) {
            throw new RuntimeException("Vehicle has already exited");
        }

        record.setExitTime(LocalDateTime.now());

        UserEntity updatedBy = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        record.setUpdatedBy(updatedBy);

        return parkingRecordRepository.save(record);
    }

    @Transactional
    public void deleteById(Long id) {
        parkingRecordRepository.deleteById(id);
    }
}
