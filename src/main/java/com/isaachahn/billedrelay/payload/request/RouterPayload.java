package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RouterPayload {
    @NotBlank
    @Size(max = 50)
    private String routerHardId;
    @NotNull
    private List<RelayPayload> relayPayloads;
}
