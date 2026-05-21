package com.retailflow.event_intelligence.kafka;

import com.retailflow.event_intelligence.api.BusinessEventRequest;
import com.retailflow.event_intelligence.api.BusinessEventResponse;
import com.retailflow.event_intelligence.service.EventProcessingService;
import com.retailflow.event_intelligence.service.BusinessEventIngestionService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BusinessEventIngestionServiceTest {

    @Test
    void publishesStateChangedWhenEventIsProcessed() {
        EventProcessingService eventProcessingService = mock(EventProcessingService.class);
        BusinessEventPublisher publisher = mock(BusinessEventPublisher.class);
        BusinessEventIngestionService service = new BusinessEventIngestionService(eventProcessingService, publisher);

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

        when(eventProcessingService.process(request))
                .thenReturn(new BusinessEventResponse("evt-1", "txn-1", "PROCESSED", "SETTLED"));

        BusinessEventResponse response = service.ingest(request);

        assertThat(response.processingStatus()).isEqualTo("PROCESSED");
        verify(publisher, times(1)).publishStateChanged(any(BusinessStateChangedEvent.class));
    }

    @Test
    void doesNotPublishWhenEventIsDuplicateIgnored() {
        EventProcessingService eventProcessingService = mock(EventProcessingService.class);
        BusinessEventPublisher publisher = mock(BusinessEventPublisher.class);
        BusinessEventIngestionService service = new BusinessEventIngestionService(eventProcessingService, publisher);

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

        when(eventProcessingService.process(request))
                .thenReturn(new BusinessEventResponse("evt-1", "txn-1", "DUPLICATE_IGNORED", "SETTLED"));

        BusinessEventResponse response = service.ingest(request);

        assertThat(response.processingStatus()).isEqualTo("DUPLICATE_IGNORED");
        verify(publisher, never()).publishStateChanged(any());
    }
}