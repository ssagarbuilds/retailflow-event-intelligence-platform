package com.retailflow.event_intelligence.api;

import com.retailflow.event_intelligence.domain.EventAudit;
import com.retailflow.event_intelligence.service.EntityQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entities")
public class EntityQueryController {

    private final EntityQueryService entityQueryService;

    public EntityQueryController(EntityQueryService entityQueryService) {
        this.entityQueryService = entityQueryService;
    }

    @GetMapping("/{entityId}/state")
    public EntityStateResponse getState(@PathVariable String entityId) {
        return entityQueryService.getState(entityId);
    }

    @GetMapping("/{entityId}/events")
    public List<EventAudit> getEvents(@PathVariable String entityId) {
        return entityQueryService.getEvents(entityId);
    }
}