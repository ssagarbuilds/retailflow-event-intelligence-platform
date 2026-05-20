package com.retailflow.event_intelligence.api;

import com.retailflow.event_intelligence.service.EventProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventProcessingService eventProcessingService;

    public EventController(EventProcessingService eventProcessingService) {
        this.eventProcessingService = eventProcessingService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BusinessEventResponse processEvent(@Valid @RequestBody BusinessEventRequest request) {
        return eventProcessingService.process(request);
    }
}

