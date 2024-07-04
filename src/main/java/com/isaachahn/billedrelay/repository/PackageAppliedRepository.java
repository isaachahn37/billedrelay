package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.PackageApplied;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageAppliedRepository extends JpaRepository<PackageApplied, Long> {
}
