package com.example.aiosbananesexport.recipient.domain.usecase;

import com.example.aiosbananesexport.recipient.domain.entity.*;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientAlreadyExistsException;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientNotFoundException;

public class RecipientService {
    private final RecipientFactory recipientFactory;
    private final RecipientRepository recipientRepository;

    public RecipientService(RecipientFactory recipientFactory, RecipientRepository recipientRepository) {
        this.recipientFactory = recipientFactory;
        this.recipientRepository = recipientRepository;
    }

    public void renameRecipient(RecipientId recipientId, Name newName) {
        recipientRepository.getById(recipientId)
                           .ifPresentOrElse(recipient -> recipient.rename(newName),
                                            () -> {
                                                throw new RecipientNotFoundException(recipientId);
                                            });
    }

    public void deleteRecipient(RecipientId recipientId) {
        recipientRepository.getById(recipientId)
                           .ifPresentOrElse(recipient -> recipientRepository.deleteById(recipientId),
                                            () -> {
                                                throw new RecipientNotFoundException(recipientId);
                                            });
    }
}
