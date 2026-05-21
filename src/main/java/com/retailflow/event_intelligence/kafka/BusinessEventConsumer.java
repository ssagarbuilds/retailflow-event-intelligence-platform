package com.retailflow.event_intelligence.kafka;

import com.retailflow.event_intelligence.api.BusinessEventRequest;
import com.retailflow.event_intelligence.config.KafkaTopics;
import com.retailflow.event_intelligence.service.BusinessEventIngestionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class BusinessEventConsumer {

    private final BusinessEventIngestionService ingestionService;

    public BusinessEventConsumer(BusinessEventIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @KafkaListener(
            topics = KafkaTopics.BUSINESS_EVENTS,
            groupId = "${spring.kafka.consumer.group-id:retailflow-event-intelligence}",
            containerFactory = "businessEventKafkaListenerContainerFactory"
    )
    public void consume(BusinessEventRequest request) {
        ingestionService.ingest(request);
    }
}