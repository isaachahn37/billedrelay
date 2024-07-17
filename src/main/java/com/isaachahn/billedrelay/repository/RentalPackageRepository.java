package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.models.entity.RentalPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalPackageRepository extends JpaRepository<RentalPackage, Long> {
    List<RentalPackage> findByRentalEntityOrderByPackageName(RentalEntity rentalEntity);
}
