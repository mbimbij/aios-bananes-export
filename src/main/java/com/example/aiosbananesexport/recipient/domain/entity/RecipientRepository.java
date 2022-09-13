package com.example.aiosbananesexport.recipient.domain.entity;

import java.util.Optional;

public interface RecipientRepository {
    Recipient saveRecipient(Recipient recipient);

    boolean exists(Name name, Address address);

    Optional<Recipient> getById(RecipientId recipientId);

    void deleteById(RecipientId recipientId);

    Optional<Recipient> getByNameAndAddress(Name name, Address address);
}
