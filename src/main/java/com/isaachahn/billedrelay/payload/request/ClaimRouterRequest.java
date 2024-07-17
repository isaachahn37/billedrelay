package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaimRouterRequest {
    @NotBlank
    private String routerHardId;
}
