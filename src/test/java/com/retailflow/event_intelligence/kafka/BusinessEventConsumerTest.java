package com.retailflow.event_intelligence.kafka;

import com.retailflow.event_intelligence.api.BusinessEventRequest;
import com.retailflow.event_intelligence.service.BusinessEventIngestionService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.Mockito.*;

class BusinessEventConsumerTest {

    @Test
    void delegatesKafkaMessageToIngestionService() {
        BusinessEventIngestionService ingestionService = mock(BusinessEventIngestionService.class);
        BusinessEventConsumer consumer = new BusinessEventConsumer(ingestionService);

        BusinessEventRequest request = new BusinessEventRequest(
                "evt-1",
                "txn-1",
                "TRANSACTION",
                "STATUS_CHANGED",
                "PAYMENT_GATEWAY",
                "PENDING",
                "SETTLED",
                new BigDecimal("100.00"),
                "USD",
                Instant.parse("2026-05-19T10:00:00Z")
        );

        consumer.consume(request);

        verify(ingestionService, times(1)).ingest(request);
    }
}