package com.isaachahn.billedrelay.payload.response;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class RentalPackagePayload {
    private Long id;
    @NotBlank
    private String packageName;
    @NotNull
    private Long addedMinutes;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String relayWhitelist;

    private String lastModifiedDate;
    @ManyToOne
    @JoinColumn(name = "rental_entity_id")
    private RentalEntity rentalEntity;

}
