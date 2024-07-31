package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.payload.request.ForceOnRelayRequest;
import com.isaachahn.billedrelay.payload.request.RelayUpdateRequest;
import com.isaachahn.billedrelay.payload.response.RelayResponse;
import com.isaachahn.billedrelay.service.RelayService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/relay")
@RequiredArgsConstructor
public class RelayController {
    private final RelayService service;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<Relay>> getAllRelays() {
        return ResponseEntity.ok(service.getAllRelays());
    }

    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<List<RelayResponse>> getRentalRelays() throws BadRequestException {
        return ResponseEntity.ok(service.getRelaysByRentalEntity());
    }

    @PutMapping("/forceon")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<RelayResponse> forceOneRelay(@Valid @RequestBody ForceOnRelayRequest request) throws ChangeSetPersister.NotFoundException, BadRequestException {
        return ResponseEntity.ok(service.forceOnRelay(request));
    }

    @PutMapping("/forceoff")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<RelayResponse> forceOffRelay(@Valid @RequestBody ForceOnRelayRequest request) throws ChangeSetPersister.NotFoundException, BadRequestException {
        return ResponseEntity.ok(service.forceOffRelay(request));
    }

    @PutMapping("/{relayId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('USER')")
    public ResponseEntity<RelayResponse> updateRelay(@PathVariable String relayId, @Valid @RequestBody RelayUpdateRequest request) throws ChangeSetPersister.NotFoundException {
        return ResponseEntity.ok(service.updateRelay(Long.parseLong(relayId), request));
    }

}
