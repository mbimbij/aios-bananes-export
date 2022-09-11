package com.example.aiosbananesexport.recipient.domain;

import java.util.Optional;

public interface RecipientRepository {
    Recipient createRecipient(Name name, Address address);

    boolean exists(Name name, Address address);

    Optional<Recipient> getById(RecipientId recipientId);
}
