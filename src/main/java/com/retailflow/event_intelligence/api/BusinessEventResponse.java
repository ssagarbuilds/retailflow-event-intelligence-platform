package com.retailflow.event_intelligence.api;

public record BusinessEventResponse(
        String eventId,
        String entityId,
        String processingStatus,
        String currentStatus
) {
}

