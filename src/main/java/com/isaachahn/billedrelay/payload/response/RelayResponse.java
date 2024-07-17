package com.isaachahn.billedrelay.payload.response;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RelayResponse {
    private Long id;
    private Integer pinNumber;
    private String relayHardId;

    private String relayName;
    private String relayDescription;

    private String relayWhitelist;
    private RentalEntity rentalEntity;

    private boolean onState;
    private boolean forcedOn;
    private String onUntil;
}
