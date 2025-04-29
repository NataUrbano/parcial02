package com.library.parcial02.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 6)
    @Pattern(regexp = "^[A-Za-z0-9]{6}$", message = "License plate must be exactly 6 alphanumeric characters")
    private String licensePlate;

    @Column(nullable = false)
    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

    @Column(nullable = false)
    private String location;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id", nullable = false)
    private VehicleTypeEntity vehicleType;

    @ManyToOne
    @JoinColumn(name = "registered_by_id", nullable = false)
    private UserEntity registeredBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_id")
    private UserEntity updatedBy;
}
