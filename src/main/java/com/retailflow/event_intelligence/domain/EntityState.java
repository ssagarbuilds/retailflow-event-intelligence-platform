package com.retailflow.event_intelligence.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "entity_state")
public class EntityState {

    @Id
    private String entityId;

    private String entityType;
    private String currentStatus;
    private String lastEventId;
    private String lastSourceSystem;
    private Instant updatedAt;

    protected EntityState() {
    }

    public EntityState(String entityId, String entityType) {
        this.entityId = entityId;
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getEntityType() {
        return entityType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getLastEventId() {
        return lastEventId;
    }

    public String getLastSourceSystem() {
        return lastSourceSystem;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void apply(String newStatus, String eventId, String sourceSystem, Instant updatedAt) {
        this.currentStatus = newStatus;
        this.lastEventId = eventId;
        this.lastSourceSystem = sourceSystem;
        this.updatedAt = updatedAt;
    }
}

