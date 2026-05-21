package com.retailflow.event_intelligence.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "event_audit")
public class EventAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;
    private String entityId;
    private String entityType;
    private String eventType;
    private String sourceSystem;
    private String previousStatus;
    private String newStatus;
    private BigDecimal amount;
    private String currency;
    private Instant occurredAt;
    private Instant processedAt;

    protected EventAudit() {
    }

    public EventAudit(
            String eventId,
            String entityId,
            String entityType,
            String eventType,
            String sourceSystem,
            String previousStatus,
            String newStatus,
            BigDecimal amount,
            String currency,
            Instant occurredAt,
            Instant processedAt
    ) {
        this.eventId = eventId;
        this.entityId = entityId;
        this.entityType = entityType;
        this.eventType = eventType;
        this.sourceSystem = sourceSystem;
        this.previousStatus = previousStatus;
        this.newStatus = newStatus;
        this.amount = amount;
        this.currency = currency;
        this.occurredAt = occurredAt;
        this.processedAt = processedAt;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getEventType() {
        return eventType;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}

