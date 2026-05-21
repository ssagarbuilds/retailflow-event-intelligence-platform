package com.retailflow.event_intelligence.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic businessEventsTopic() {
        return TopicBuilder.name(KafkaTopics.BUSINESS_EVENTS)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic businessStateChangedTopic() {
        return TopicBuilder.name(KafkaTopics.BUSINESS_STATE_CHANGED)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic businessEventsDeadLetterTopic() {
        return TopicBuilder.name(KafkaTopics.BUSINESS_EVENTS_DEAD_LETTER)
                .partitions(1)
                .replicas(1)
                .build();
    }
}