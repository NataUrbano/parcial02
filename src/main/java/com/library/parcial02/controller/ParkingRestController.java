package com.library.parcial02.controller;

import com.library.parcial02.model.ParkingRecordEntity;
import com.library.parcial02.service.ParkingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
@Tag(name = "Parking", description = "Parking Management API")

public class ParkingRestController {

    @Autowired
    private ParkingRecordService parkingRecordService;

    @GetMapping
    @Operation(summary = "Get all parking records")
    public ResponseEntity<List<ParkingRecordEntity>> getAllParking() {
        return ResponseEntity.ok(parkingRecordService.findAll());
    }

    @GetMapping("/active")
    @Operation(summary = "Get active parking records")
    public ResponseEntity<List<ParkingRecordEntity>> getActiveParking() {
        return ResponseEntity.ok(parkingRecordService.findAllActive());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get parking record by ID")
    public ResponseEntity<ParkingRecordEntity> getParkingById(@PathVariable Long id) {
        return parkingRecordService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Create parking record")
    public ResponseEntity<ParkingRecordEntity> createParking(@Valid @RequestBody ParkingRecordEntity parkingRecord, Principal principal) {
        try {
            ParkingRecordEntity created = parkingRecordService.save(parkingRecord, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update parking record")
    public ResponseEntity<ParkingRecordEntity> updateParking(@PathVariable Long id, @Valid @RequestBody ParkingRecordEntity parkingRecord, Principal principal) {
        try {
            return parkingRecordService.findById(id)
                    .map(existingRecord -> {
                        parkingRecord.setId(id);
                        return ResponseEntity.ok(parkingRecordService.save(parkingRecord, principal.getName()));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/exit")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Register vehicle exit")
    public ResponseEntity<ParkingRecordEntity> registerExit(@PathVariable Long id, Principal principal) {
        try {
            ParkingRecordEntity updated = parkingRecordService.registerExit(id, principal.getName());
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/location")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'ATTENDANT')")
    @Operation(summary = "Update vehicle location")
    public ResponseEntity<ParkingRecordEntity> updateLocation(@PathVariable Long id, @RequestBody ParkingRecordEntity parkingRecord, Principal principal) {
        try {
            return parkingRecordService.findById(id)
                    .map(existingRecord -> {
                        existingRecord.setLocation(parkingRecord.getLocation());
                        return ResponseEntity.ok(parkingRecordService.save(existingRecord, principal.getName()));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete parking record")
    public ResponseEntity<Void> deleteParking(@PathVariable Long id) {
        try {
            return parkingRecordService.findById(id)
                    .map(parkingRecord -> {
                        parkingRecordService.deleteById(id);
                        return ResponseEntity.noContent().<Void>build();
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

