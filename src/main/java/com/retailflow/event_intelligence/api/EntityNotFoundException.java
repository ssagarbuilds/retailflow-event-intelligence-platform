package com.retailflow.event_intelligence.api;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityId) {
        super("Entity not found: " + entityId);
    }
}

