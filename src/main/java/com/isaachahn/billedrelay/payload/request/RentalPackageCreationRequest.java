package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RentalPackageCreationRequest {
    @NotBlank
    private String packageName;
    @NotNull
    private Long addedMinutes;
    @NotNull
    private BigDecimal price;
    private String relayWhitelist;
}
