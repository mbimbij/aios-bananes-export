package com.example.aiosbananesexport.recipient.domain;

public class RecipientService {
    private RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public void createRecipient(Name name, String address, String postalCode, String city, String country) {
        recipientRepository.createRecipient(name, address, postalCode, city, country);
    }
}
