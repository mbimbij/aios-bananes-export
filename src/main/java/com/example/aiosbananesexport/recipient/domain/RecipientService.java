package com.example.aiosbananesexport.recipient.domain;

public class RecipientService {
    private RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public void createRecipient(Name name, Address address) {
        recipientRepository.createRecipient(name, address);
    }
}
