package com.isaachahn.billedrelay.payload.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class PackageAppliedReportSummary {
    private String date;
    private List<PackageAppliedReport> packageAppliedReports;
    private BigDecimal total;
}
