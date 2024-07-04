package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PackageToRelayAssignmentRequest {
    @NotNull
    private Long rentalPackageId;
    @NotNull
    private Long relayId;
}
