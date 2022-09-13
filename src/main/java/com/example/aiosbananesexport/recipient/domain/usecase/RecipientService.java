package com.example.aiosbananesexport.recipient.domain.usecase;

import com.example.aiosbananesexport.recipient.domain.entity.*;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientAlreadyExistsException;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientNotFoundException;

public class RecipientService {
    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public void deleteRecipient(RecipientId recipientId) {
        recipientRepository.getById(recipientId)
                           .ifPresentOrElse(recipient -> recipientRepository.deleteById(recipientId),
                                            () -> {
                                                throw new RecipientNotFoundException(recipientId);
                                            });
    }
}