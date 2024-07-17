package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.models.entity.Router;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouterRepository extends JpaRepository<Router, Long> {
    List<Router> findByRentalEntity(RentalEntity rentalEntity);
    Router findByRouterHardId(String routerHardId);
}
