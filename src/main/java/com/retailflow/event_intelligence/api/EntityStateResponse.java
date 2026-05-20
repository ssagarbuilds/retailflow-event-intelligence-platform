package com.retailflow.event_intelligence.api;

import java.time.Instant;

public record EntityStateResponse(
        String entityId,
        String entityType,
        String currentStatus,
        String lastEventId,
        String lastSourceSystem,
        Instant updatedAt
) {
}

