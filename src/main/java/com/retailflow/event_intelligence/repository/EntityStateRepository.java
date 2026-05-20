package com.retailflow.event_intelligence.repository;

import com.retailflow.event_intelligence.domain.EntityState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityStateRepository extends JpaRepository<EntityState, String> {
}

