package com.retailflow.event_intelligence.service;

import com.retailflow.event_intelligence.api.BusinessEventRequest;
import com.retailflow.event_intelligence.api.BusinessEventResponse;
import com.retailflow.event_intelligence.kafka.BusinessEventPublisher;
import com.retailflow.event_intelligence.kafka.BusinessStateChangedEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BusinessEventIngestionService {

    private final EventProcessingService eventProcessingService;
    private final BusinessEventPublisher businessEventPublisher;

    public BusinessEventIngestionService(
            EventProcessingService eventProcessingService,
            BusinessEventPublisher businessEventPublisher
    ) {
        this.eventProcessingService = eventProcessingService;
        this.businessEventPublisher = businessEventPublisher;
    }

    public BusinessEventResponse ingest(BusinessEventRequest request) {
        BusinessEventResponse response = eventProcessingService.process(request);

        if ("PROCESSED".equals(response.processingStatus())) {
            businessEventPublisher.publishStateChanged(
                    new BusinessStateChangedEvent(
                            response.eventId(),
                            response.entityId(),
                            request.entityType(),
                            request.previousStatus(),
                            request.newStatus(),
                            response.processingStatus(),
                            request.sourceSystem(),
                            Instant.now()
                    )
            );
        }

        return response;
    }
}