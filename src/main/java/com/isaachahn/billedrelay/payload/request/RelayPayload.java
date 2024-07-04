package com.isaachahn.billedrelay.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelayPayload {
    @NotNull
    private Integer pinNumber;
    @NotBlank
    @Size(max = 50)
    private String relayHardId;

    @NotBlank
    @Size(max = 50)
    private String relayName;
    @NotBlank
    private String relayDescription;

    private String relayWhitelist;
}
