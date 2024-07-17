package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ForceOnRelayRequest {
    @NotNull
    private Long relayId;
}
