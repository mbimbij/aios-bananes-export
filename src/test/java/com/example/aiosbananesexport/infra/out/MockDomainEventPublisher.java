package com.example.aiosbananesexport.infra.out;

import com.example.aiosbananesexport.domain.DomainEvent;
import com.example.aiosbananesexport.domain.DomainEventPublisher;

import java.util.ArrayList;
import java.util.List;

public class MockDomainEventPublisher implements DomainEventPublisher {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    @Override
    public void send(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
}
