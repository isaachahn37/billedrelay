package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.PackageApplied;
import com.isaachahn.billedrelay.models.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageAppliedRepository extends JpaRepository<PackageApplied, Long> {
    List<PackageApplied> findByAppliedTimeStampGreaterThanAndAppliedTimeStampLessThanAndRentalEntityOrderByAppliedTimeStamp(long greaterThan, long lessThan, RentalEntity rentalEntity);
}
