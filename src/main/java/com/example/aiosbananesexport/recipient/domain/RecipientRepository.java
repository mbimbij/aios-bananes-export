package com.example.aiosbananesexport.recipient.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipientRepository {
    Recipient createRecipient(String firstName, String lastName, String address, String postalCode, String city, String country);

    Mono<Recipient> getById(String recipientId);
}
