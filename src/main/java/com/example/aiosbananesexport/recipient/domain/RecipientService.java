package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.recipient.exception.RecipientAlreadyExistsException;
import com.example.aiosbananesexport.recipient.exception.RecipientNotFoundException;

public class RecipientService {
    private RecipientRepository recipientRepository;

    public RecipientService(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public Recipient createRecipient(Name name, Address address) {
        if (recipientRepository.exists(name, address)) {
            throw new RecipientAlreadyExistsException(name, address);
        }
        Recipient recipient = recipientRepository.createRecipient(name, address);
        recipientRepository.saveRecipient(recipient);
        return recipient;
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
