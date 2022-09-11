package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.recipient.exception.RecipientAlreadyExistsException;

public class RecipientService {
    private RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public void createRecipient(Name name, Address address) {
        if (recipientRepository.exists(name, address)) {
            throw new RecipientAlreadyExistsException(name, address);
        }
        recipientRepository.createRecipient(name, address);
    }
}
