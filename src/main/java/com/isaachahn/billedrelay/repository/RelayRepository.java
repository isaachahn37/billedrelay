package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.Relay;
import com.isaachahn.billedrelay.models.entity.RentalEntity;
import com.isaachahn.billedrelay.models.entity.Router;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelayRepository extends JpaRepository<Relay, Long> {
    List<Relay> findByRouter(Router router);
    List<Relay> findByRentalEntityOrderByRelayName(RentalEntity rentalEntity);
}
