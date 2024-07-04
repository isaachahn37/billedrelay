package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalEntityCreateRequest {
    @NotBlank
    @Size(max = 50)
    private String rentalName;
}
