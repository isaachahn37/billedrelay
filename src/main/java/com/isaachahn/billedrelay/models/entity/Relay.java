package com.isaachahn.billedrelay.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Data
@Table(name = "relay",
        uniqueConstraints = {@UniqueConstraint(columnNames = "relay_hard_id")})
@Accessors(chain = true)
public class Relay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long onUntil;
    private boolean forcedOn;
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

    @ManyToOne
    @JoinColumn(name = "router_id")
    private Router router;

    @ManyToOne
    @JoinColumn(name = "rental_entity_id")
    private RentalEntity rentalEntity;
}
