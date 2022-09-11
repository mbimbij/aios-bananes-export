package com.example.aiosbananesexport.recipient.infra.out;

import com.example.aiosbananesexport.recipient.domain.*;

import java.util.*;

public class InMemoryRecipientRepository implements RecipientRepository {
    private Map<RecipientId, Recipient> recipients = new HashMap<>();

    @Override
    public Recipient createRecipient(Name name, Address address) {
        Recipient recipient = new Recipient(generateRecipientId(), name, address);
        recipients.put(recipient.getRecipientId(), recipient);
        return recipient;
    }

    @Override
    public boolean exists(Name name, Address address) {
        return recipients.values()
                         .stream()
                         .anyMatch(recipient -> Objects.equals(recipient.getName(), name) &&
                                                Objects.equals(recipient.getAddress(), address));
    }

    public RecipientId generateRecipientId() {
        return new RecipientId(UUID.randomUUID().toString());
    }

    @Override
    public Optional<Recipient> getById(RecipientId recipientId) {
        Recipient recipient = recipients.get(recipientId);
        return Optional.ofNullable(recipient);
    }
}
