package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.RentalPackage;
import com.isaachahn.billedrelay.payload.request.RentalPackageCreationRequest;
import com.isaachahn.billedrelay.payload.response.RentalPackagePayload;
import com.isaachahn.billedrelay.service.RentalPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rentalpackage")
@RequiredArgsConstructor
public class RentalPackageController {
    private final RentalPackageService service;

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<RentalPackagePayload> createRentalPackage(@Valid @RequestBody RentalPackagePayload request) throws BadRequestException {
        return ResponseEntity.ok(service.createRentalPackage(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<RentalPackagePayload>> getAllRentalPackages() throws BadRequestException {
        return ResponseEntity.ok(service.getAllRentalPackages());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<RentalPackagePayload>  updateRentalPackage(@PathVariable String id, @RequestBody RentalPackageCreationRequest request) throws BadRequestException {
        return ResponseEntity.ok(service.updateRentalPackage(request, Long.parseLong(id)));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<RentalPackage> deleteRentalPackage(RentalPackage rentalPackage) throws BadRequestException {
        return ResponseEntity.ok(service.deleteRentalPackage(rentalPackage));
    }
}
