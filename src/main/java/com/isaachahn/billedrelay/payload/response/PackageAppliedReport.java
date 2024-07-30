package com.isaachahn.billedrelay.payload.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@Getter
@Setter
@Accessors(chain = true)
public class PackageAppliedReport {
    private String dateTimeApplied;
    private Long minutesAdded;
    private String packageName;
    private BigDecimal packageAppliedAmount;
    private String relayName;
    private String relayHardId;
    private String relayWhitelist;
    private String relayDescription;
}
