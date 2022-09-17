package com.example.aiosbananesexport;

import com.example.aiosbananesexport.domain.DomainEvent;
import com.example.aiosbananesexport.domain.DomainEventSender;
import com.example.aiosbananesexport.domain.OrderCreatedEvent;

import java.util.ArrayList;
import java.util.List;

public class MockDomainEventSender implements DomainEventSender {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    @Override
    public void send(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
}
