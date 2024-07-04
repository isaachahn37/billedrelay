package com.isaachahn.billedrelay.service;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.payload.request.RentalEntityCreateRequest;
import com.isaachahn.billedrelay.repository.RentalEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalEntityService {
    private final RentalEntityRepository repository;

    public RentalEntity create(RentalEntityCreateRequest request) {
        RentalEntity rentalEntity = new RentalEntity().setRentalName(request.getRentalName());
        return repository.save(rentalEntity);
    }

}
