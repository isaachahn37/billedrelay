package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.PackageApplied;
import com.isaachahn.billedrelay.models.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageAppliedRepository extends JpaRepository<PackageApplied, Long> {
    List<PackageApplied> findByRentalEntity(RentalEntity rentalEntity);
    List<PackageApplied> findByAppliedTimeStampGreaterThanAndAppliedTimeStampIsLessThan(Long start, Long end);
//    @Query("SELECT p FROM PackageApplied p WHERE p.appliedTimeStamp > :beginningTimestamp AND p.appliedTimeStamp < :endingTimestamp")
//    List<PackageApplied> findByAppliedTimeStampBetween(@Param("beginningTimestamp") Long beginningTimestamp, @Param("endingTimestamp") Long endingTimestamp);
//    @Query("SELECT p FROM PackageApplied p WHERE p.appliedTimeStamp BETWEEN :startTime AND :endTime")
//    List<PackageApplied> findByAppliedTimeStampBetween(@Param("startTime") Long startTime, @Param("endTime") Long endTime);
//    List<PackageApplied> findByAppliedTimeStampBetween(Long greaterThan, Long lessThan);
//    @Query("SELECT p FROM PackageApplied p WHERE p.appliedTimeStamp BETWEEN :startTime AND :endTime AND p.rentalEntity = :rentalEntity")
//    List<PackageApplied> findByAppliedTimeStampBetweenAndRentalEntity(
//            @Param("startTime") Long startTime,
//            @Param("endTime") Long endTime,
//            @Param("rentalEntity") RentalEntity rentalEntity);
}
