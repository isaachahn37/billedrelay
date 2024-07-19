package com.isaachahn.billedrelay.payload.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RouterRelay {
    private Integer pinNumber;
    private String relayHardId;
    private boolean onState;
}
