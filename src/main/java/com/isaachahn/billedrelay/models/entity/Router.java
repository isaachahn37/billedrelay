package com.isaachahn.billedrelay.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "router")
@Data
@Accessors(chain = true)
public class Router {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 50)
    private String routerHardId;

    @ManyToOne
    @JoinColumn(name = "rental_entity_id")
    private RentalEntity rentalEntity;
}
