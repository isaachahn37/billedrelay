package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.payload.request.RentalEntityCreateRequest;
import com.isaachahn.billedrelay.service.RentalEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rentalentity")
@RequiredArgsConstructor
public class RentalEntityController {
    private final RentalEntityService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RentalEntity> create(@RequestBody RentalEntityCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
}
