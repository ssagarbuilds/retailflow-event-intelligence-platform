package com.retailflow.event_intelligence.service;

import com.retailflow.event_intelligence.api.EntityNotFoundException;
import com.retailflow.event_intelligence.api.EntityStateResponse;
import com.retailflow.event_intelligence.domain.EventAudit;
import com.retailflow.event_intelligence.repository.EntityStateRepository;
import com.retailflow.event_intelligence.repository.EventAuditRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityQueryService {

    private final EntityStateRepository entityStateRepository;
    private final EventAuditRepository eventAuditRepository;

    public EntityQueryService(
            EntityStateRepository entityStateRepository,
            EventAuditRepository eventAuditRepository
    ) {
        this.entityStateRepository = entityStateRepository;
        this.eventAuditRepository = eventAuditRepository;
    }

    @Cacheable(cacheNames = "entityState", key = "#entityId")
    public EntityStateResponse getState(String entityId) {
        return entityStateRepository.findById(entityId)
                .map(state -> new EntityStateResponse(
                        state.getEntityId(),
                        state.getEntityType(),
                        state.getCurrentStatus(),
                        state.getLastEventId(),
                        state.getLastSourceSystem(),
                        state.getUpdatedAt()
                ))
                .orElseThrow(() -> new EntityNotFoundException(entityId));
    }

    public List<EventAudit> getEvents(String entityId) {
        return eventAuditRepository.findByEntityIdOrderByOccurredAtDesc(entityId);
    }
}