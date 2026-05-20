package com.retailflow.event_intelligence.repository;

import com.retailflow.event_intelligence.domain.EventAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventAuditRepository extends JpaRepository<EventAudit, Long> {

    List<EventAudit> findByEntityIdOrderByOccurredAtDesc(String entityId);
}

