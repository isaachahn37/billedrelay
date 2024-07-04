package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.PackageApplied;
import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.payload.request.PackageToRelayAssignmentRequest;
import com.isaachahn.billedrelay.service.RelayPackageApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relaypackageapplication")
@RequiredArgsConstructor
public class RelayPackageApplicationController {
    private final RelayPackageApplicationService service;
    public ResponseEntity<PackageApplied> assignPackageToRelay(@Valid @RequestBody PackageToRelayAssignmentRequest request) throws BadRequestException {
        return ResponseEntity.ok(service.assignPackageToRelay(request));
    }
}
