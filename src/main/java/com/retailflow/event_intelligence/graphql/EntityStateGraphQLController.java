package com.retailflow.event_intelligence.graphql;

import com.retailflow.event_intelligence.domain.EntityState;
import com.retailflow.event_intelligence.repository.EntityStateRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class EntityStateGraphQLController {

    private final EntityStateRepository entityStateRepository;

    public EntityStateGraphQLController(EntityStateRepository entityStateRepository) {
        this.entityStateRepository = entityStateRepository;
    }

    @QueryMapping
    public EntityState entityState(@Argument String entityId) {
        return entityStateRepository.findById(entityId)
                .orElse(null);
    }
}