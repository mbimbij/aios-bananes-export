package com.example.aiosbananesexport.recipient.infra.out;

import com.example.aiosbananesexport.recipient.domain.Address;
import com.example.aiosbananesexport.recipient.domain.Name;
import com.example.aiosbananesexport.recipient.domain.Recipient;
import com.example.aiosbananesexport.recipient.domain.RecipientRepository;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryRecipientRepository implements RecipientRepository {
    private Map<String, Recipient> recipients = new HashMap<>();

    @Override
    public Recipient createRecipient(Name name, Address address) {
        Recipient recipient = new Recipient(generateRecipientId(), name, address);
        recipients.put(recipient.getRecipientId(), recipient);
        return recipient;
    }

    public String generateRecipientId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Mono<Recipient> getById(String recipientId) {
        Recipient recipient = recipients.get(recipientId);
        return Mono.justOrEmpty(recipient);
    }
}
