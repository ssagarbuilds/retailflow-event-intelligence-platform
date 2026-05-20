package com.retailflow.event_intelligence.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;

public record BusinessEventRequest(
        @NotBlank String eventId,
        @NotBlank String entityId,
        @NotBlank String entityType,
        @NotBlank String eventType,
        @NotBlank String sourceSystem,
        String previousStatus,
        @NotBlank String newStatus,
        @PositiveOrZero BigDecimal amount,
        String currency,
        @NotNull Instant occurredAt
) {
}

