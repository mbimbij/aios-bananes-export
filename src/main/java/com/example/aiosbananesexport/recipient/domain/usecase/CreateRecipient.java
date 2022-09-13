package com.example.aiosbananesexport.recipient.domain.usecase;

import com.example.aiosbananesexport.recipient.domain.entity.*;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientAlreadyExistsException;

public class CreateRecipient {
    private final RecipientFactory recipientFactory;
    private final RecipientRepository recipientRepository;

    public CreateRecipient(RecipientFactory recipientFactory, RecipientRepository recipientRepository) {
        this.recipientFactory = recipientFactory;
        this.recipientRepository = recipientRepository;
    }

    public Recipient createRecipient(Name name, Address address) {
        if (recipientRepository.exists(name, address)) {
            throw new RecipientAlreadyExistsException(name, address);
        }
        Recipient recipient = recipientFactory.createRecipient(name, address);
        recipientRepository.saveRecipient(recipient);
        return recipient;
    }
}
