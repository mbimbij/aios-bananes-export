package com.example.aiosbananesexport.domain;

public interface DomainEventSender {
    void send(DomainEvent event);
}
