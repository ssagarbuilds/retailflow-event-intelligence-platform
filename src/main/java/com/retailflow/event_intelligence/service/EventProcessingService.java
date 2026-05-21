package com.retailflow.event_intelligence.service;

import com.retailflow.event_intelligence.api.BusinessEventRequest;
import com.retailflow.event_intelligence.api.BusinessEventResponse;
import com.retailflow.event_intelligence.domain.EntityState;
import com.retailflow.event_intelligence.domain.EventAudit;
import com.retailflow.event_intelligence.domain.ProcessedEvent;
import com.retailflow.event_intelligence.repository.EntityStateRepository;
import com.retailflow.event_intelligence.repository.EventAuditRepository;
import com.retailflow.event_intelligence.repository.ProcessedEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.CacheEvict;

import java.time.Instant;

@Service
public class EventProcessingService {

    private final EntityStateRepository entityStateRepository;
    private final EventAuditRepository eventAuditRepository;
    private final ProcessedEventRepository processedEventRepository;

    public EventProcessingService(
            EntityStateRepository entityStateRepository,
            EventAuditRepository eventAuditRepository,
            ProcessedEventRepository processedEventRepository
    ) {
        this.entityStateRepository = entityStateRepository;
        this.eventAuditRepository = eventAuditRepository;
        this.processedEventRepository = processedEventRepository;
    }

    @CacheEvict(value = "entityState", key = "#request.entityId")
    @Transactional
    public BusinessEventResponse process(BusinessEventRequest request) {
        if (processedEventRepository.existsById(request.eventId())) {
            EntityState existingState = entityStateRepository.findById(request.entityId()).orElse(null);
            String currentStatus = existingState == null ? "UNKNOWN" : existingState.getCurrentStatus();
            return new BusinessEventResponse(
                    request.eventId(),
                    request.entityId(),
                    "DUPLICATE_IGNORED",
                    currentStatus
            );
        }

        Instant now = Instant.now();
        EntityState state = entityStateRepository
                .findById(request.entityId())
                .orElseGet(() -> new EntityState(request.entityId(), request.entityType()));

        state.apply(request.newStatus(), request.eventId(), request.sourceSystem(), now);
        entityStateRepository.save(state);

        eventAuditRepository.save(new EventAudit(
                request.eventId(),
                request.entityId(),
                request.entityType(),
                request.eventType(),
                request.sourceSystem(),
                request.previousStatus(),
                request.newStatus(),
                request.amount(),
                request.currency(),
                request.occurredAt(),
                now
        ));

        processedEventRepository.save(new ProcessedEvent(request.eventId(), now));

        return new BusinessEventResponse(
                request.eventId(),
                request.entityId(),
                "PROCESSED",
                state.getCurrentStatus()
        );
    }
}

