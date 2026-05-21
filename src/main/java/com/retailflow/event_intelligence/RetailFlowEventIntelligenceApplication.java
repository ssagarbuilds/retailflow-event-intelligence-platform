package com.retailflow.event_intelligence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RetailFlowEventIntelligenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetailFlowEventIntelligenceApplication.class, args);
    }
}

