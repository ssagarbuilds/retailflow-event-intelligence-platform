package com.retailflow.event_intelligence.kafka;

import com.retailflow.event_intelligence.config.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BusinessEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public BusinessEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishStateChanged(BusinessStateChangedEvent event) {
        kafkaTemplate.send(KafkaTopics.BUSINESS_STATE_CHANGED, event.entityId(), event);
    }
}