package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.Router;
import com.isaachahn.billedrelay.payload.request.ClaimRouterRequest;
import com.isaachahn.billedrelay.payload.request.RouterPayload;
import com.isaachahn.billedrelay.payload.response.RelayResponse;
import com.isaachahn.billedrelay.payload.response.RouterRelay;
import com.isaachahn.billedrelay.payload.response.RouterResponse;
import com.isaachahn.billedrelay.service.RouterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/router")
@RequiredArgsConstructor
public class RouterController {
    private final RouterService routerService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Router> registerRouter(@Valid @RequestBody RouterPayload payload) throws BadRequestException {
        return ResponseEntity.ok(routerService.createRouter(payload));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    public ResponseEntity<List<RouterResponse>> getAllRouters() throws BadRequestException {
        return ResponseEntity.ok(routerService.getRouters());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<RouterResponse>> getAllRoutersByAdmin() {
        return ResponseEntity.ok(routerService.getAllRouters());
    }

    @PutMapping("/claim")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<RouterResponse> claimRouter(@Valid @RequestBody ClaimRouterRequest request) throws BadRequestException {
        return ResponseEntity.ok(routerService.claimRouter(request.getRouterHardId()));
    }

    @GetMapping("/relays/{routerhardid}")
    public ResponseEntity<List<RelayResponse>> getRouterRelay(@PathVariable String routerhardid) throws BadRequestException {
        return ResponseEntity.ok(routerService.getRouterRelays(routerhardid));
    }

    @GetMapping("/relays-simplified/{routerhardid}")
    public ResponseEntity<List<RouterRelay>> getRouterRelaySimplified(@PathVariable String routerhardid) throws BadRequestException {
        return ResponseEntity.ok(routerService.getRouterRelaysSimplified(routerhardid));
    }
}
