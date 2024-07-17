package com.isaachahn.billedrelay.payload.response;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.payload.request.RelayPayload;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RouterResponse {
    private Long id;
    private String routerHardId;
    private List<RelayResponse> relays;
    private RentalEntity rentalEntity;
}
