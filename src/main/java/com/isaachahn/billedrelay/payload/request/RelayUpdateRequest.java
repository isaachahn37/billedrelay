package com.isaachahn.billedrelay.payload.request;

import lombok.Data;

@Data
public class RelayUpdateRequest {
    private String relayName;
    private String relayDescription;
    private String relayWhitelist;
}
