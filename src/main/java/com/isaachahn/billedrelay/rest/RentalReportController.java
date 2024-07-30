package com.isaachahn.billedrelay.rest;

import com.isaachahn.billedrelay.payload.response.PackageAppliedReportSummary;
import com.isaachahn.billedrelay.service.RelayPackageApplicationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class RentalReportController {
    private final RelayPackageApplicationService service;
    @GetMapping("/{date}")
    public ResponseEntity<PackageAppliedReportSummary> getReport(@PathVariable String date) throws BadRequestException {
        return ResponseEntity.ok(service.getPackageAppliedReportSummary(date));
    }
}
