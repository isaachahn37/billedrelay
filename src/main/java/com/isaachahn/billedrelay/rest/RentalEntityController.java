package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.payload.request.RentalEntityCreateRequest;
import com.isaachahn.billedrelay.service.RentalEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentalentity")
@RequiredArgsConstructor
public class RentalEntityController {
    private final RentalEntityService service;

    @PostMapping
    public ResponseEntity<RentalEntity> create(@RequestBody RentalEntityCreateRequest request) {
        return ResponseEntity.ok(service.create(request));
    }
}
