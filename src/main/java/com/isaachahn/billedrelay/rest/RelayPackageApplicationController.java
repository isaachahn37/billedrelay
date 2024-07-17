package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.PackageApplied;
import com.isaachahn.billedrelay.payload.request.PackageToRelayAssignmentRequest;
import com.isaachahn.billedrelay.service.RelayPackageApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/relaypackageapplication")
@RequiredArgsConstructor
public class RelayPackageApplicationController {
    private final RelayPackageApplicationService service;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PackageApplied> assignPackageToRelay(@Valid @RequestBody PackageToRelayAssignmentRequest request) throws BadRequestException {
        return ResponseEntity.ok(service.assignPackageToRelay(request));
    }
}
