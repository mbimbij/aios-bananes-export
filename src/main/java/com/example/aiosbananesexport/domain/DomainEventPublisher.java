package com.example.aiosbananesexport.domain;

public interface DomainEventPublisher {
    void send(DomainEvent event);
}
