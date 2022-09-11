package com.example.aiosbananesexport.recipient.domain;

import reactor.core.publisher.Mono;

public interface RecipientRepository {
    Recipient createRecipient(Name name, Address address);

    Mono<Recipient> getById(String recipientId);
}
