package com.isaachahn.billedrelay.repository;

import com.isaachahn.billedrelay.models.entity.Router;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouterRepository extends JpaRepository<Router, Long> {
}
