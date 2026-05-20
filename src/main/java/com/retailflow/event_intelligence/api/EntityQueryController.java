package com.retailflow.event_intelligence.api;

import com.retailflow.event_intelligence.domain.EventAudit;
import com.retailflow.event_intelligence.repository.EntityStateRepository;
import com.retailflow.event_intelligence.repository.EventAuditRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/entities")
public class EntityQueryController {

    private final EntityStateRepository entityStateRepository;
    private final EventAuditRepository eventAuditRepository;

    public EntityQueryController(
            EntityStateRepository entityStateRepository,
            EventAuditRepository eventAuditRepository
    ) {
        this.entityStateRepository = entityStateRepository;
        this.eventAuditRepository = eventAuditRepository;
    }

    @GetMapping("/{entityId}/state")
    public EntityStateResponse getState(@PathVariable String entityId) {
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

    @GetMapping("/{entityId}/events")
    public List<EventAudit> getEvents(@PathVariable String entityId) {
        return eventAuditRepository.findByEntityIdOrderByOccurredAtDesc(entityId);
    }
}

