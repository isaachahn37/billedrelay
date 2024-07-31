package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.models.entity.PackageApplied;
import com.isaachahn.billedrelay.payload.response.PackageAppliedReportSummary;
import com.isaachahn.billedrelay.service.RelayPackageApplicationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class RentalReportController {
    private final RelayPackageApplicationService service;
    @GetMapping("/{date}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<PackageAppliedReportSummary> getReport(@PathVariable String date) throws BadRequestException {
        return ResponseEntity.ok(service.getPackageAppliedReportSummary(date));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PackageApplied>> getReportAll() throws BadRequestException {
        return ResponseEntity.ok(service.getAllRentalPackageApplied());
    }


}
