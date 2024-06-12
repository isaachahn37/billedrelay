package com.isaachahn.billedrelay.models.entity;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class RentalPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String packageName;
    private long addedTimeLength;
    private LocalDateTime createdTime;
    private Relay targetRelay;
}
