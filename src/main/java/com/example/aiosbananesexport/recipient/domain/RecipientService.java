package com.example.aiosbananesexport.recipient.domain;

public class RecipientService {
    private RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public void createRecipient(String firstName, String lastName, String address, String postalCode, String city, String country) {
        recipientRepository.createRecipient(firstName, lastName, address, postalCode, city, country);
    }
}
