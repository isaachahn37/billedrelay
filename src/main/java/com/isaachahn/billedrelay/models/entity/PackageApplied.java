package com.isaachahn.billedrelay.models.entity;

import com.isaachahn.billedrelay.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "package_applied")
@Accessors(chain = true)
public class PackageApplied {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long appliedTimeStamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdByUser;

    @ManyToOne
    @JoinColumn(name = "rental_package_id")
    private RentalPackage rentalPackage;

    @ManyToOne
    @JoinColumn(name = "relay_id")
    private Relay relay;

    @ManyToOne
    @JoinColumn(name = "rental_entity_id")
    private RentalEntity rentalEntity;
}
