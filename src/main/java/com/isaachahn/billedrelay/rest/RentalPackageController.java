package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.RentalPackage;
import com.isaachahn.billedrelay.payload.request.RentalPackageCreationRequest;
import com.isaachahn.billedrelay.service.RentalPackageService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentalpackage")
@RequiredArgsConstructor
public class RentalPackageController {
    private final RentalPackageService service;

    @PostMapping
    public ResponseEntity<RentalPackage> createRentalPackage(RentalPackageCreationRequest request) throws BadRequestException {
        return ResponseEntity.ok(service.createRentalPackage(request));
    }

    @GetMapping
    public ResponseEntity<List<RentalPackage>> getAllRentalPackages() throws BadRequestException {
        return ResponseEntity.ok(service.getAllRentalPackages());
    }

    @PutMapping
    public ResponseEntity<RentalPackage>  updateRentalPackage(RentalPackage rentalPackage) throws BadRequestException {
        return ResponseEntity.ok(service.updateRentalPackage(rentalPackage));
    }

    @DeleteMapping
    public ResponseEntity<RentalPackage> deleteRentalPackage(RentalPackage rentalPackage) throws BadRequestException {
        return ResponseEntity.ok(service.deleteRentalPackage(rentalPackage));
    }
}
