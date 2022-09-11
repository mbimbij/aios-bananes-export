package com.example.aiosbananesexport.recipient.domain;

import reactor.core.publisher.Mono;

public interface RecipientRepository {
    Recipient createRecipient(Name name, String address, String postalCode, String city, String country);

    Mono<Recipient> getById(String recipientId);
}
