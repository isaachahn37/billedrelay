package com.isaachahn.billedrelay.models.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Table(name = "rental_package")
@Data
@Accessors(chain = true)
public class RentalPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String packageName;
    @NotNull
    private Long milisAddedOnTime;
    @NotNull
    private BigDecimal price;
    @NotBlank
    private String relayWhitelist;
    private Long lastModified;

    @ManyToOne
    @JoinColumn(name = "rental_entity_id")
    private RentalEntity rentalEntity;
}