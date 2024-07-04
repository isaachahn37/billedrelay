package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.Relay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RelayRepository extends JpaRepository<Relay, Long> {

}
