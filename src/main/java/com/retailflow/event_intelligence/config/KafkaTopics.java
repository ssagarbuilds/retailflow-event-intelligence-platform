package com.retailflow.event_intelligence.config;

public final class KafkaTopics {

    private KafkaTopics() {
    }

    public static final String BUSINESS_EVENTS = "business-events";
    public static final String BUSINESS_STATE_CHANGED = "business-state-changed";
    public static final String BUSINESS_EVENTS_DEAD_LETTER = "business-events-dead-letter";
}