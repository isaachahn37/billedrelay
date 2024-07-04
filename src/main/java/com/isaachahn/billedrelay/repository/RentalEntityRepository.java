package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalEntityRepository extends JpaRepository<RentalEntity, Long> {
}
