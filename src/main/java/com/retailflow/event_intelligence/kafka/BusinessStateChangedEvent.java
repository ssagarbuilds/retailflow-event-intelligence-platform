package com.retailflow.event_intelligence.kafka;

import java.time.Instant;

public record BusinessStateChangedEvent(
        String eventId,
        String entityId,
        String entityType,
        String previousStatus,
        String newStatus,
        String processingStatus,
        String sourceSystem,
        Instant processedAt
) {
}
