package com.example.aiosbananesexport.recipient.domain.entity;

import java.util.UUID;

public class RecipientFactory {
    public RecipientId generateRecipientId() {
        return new RecipientId(UUID.randomUUID().toString());
    }

    public Recipient createRecipient(Name name, Address address) {
        return new Recipient(generateRecipientId(), name, address);
    }
}
