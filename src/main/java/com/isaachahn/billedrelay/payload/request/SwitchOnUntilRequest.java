package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SwitchOnUntilRequest {
    @NotNull
    private Long fromRelayId;

    @NotNull
    private Long toRelayId;
}