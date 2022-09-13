package com.example.aiosbananesexport.recipient.domain.usecase;

import com.example.aiosbananesexport.recipient.domain.entity.Name;
import com.example.aiosbananesexport.recipient.domain.entity.RecipientId;
import com.example.aiosbananesexport.recipient.domain.entity.RecipientRepository;
import com.example.aiosbananesexport.recipient.domain.exception.RecipientNotFoundException;

public class RenameRecipient {

    private final RecipientRepository recipientRepository;

    public RenameRecipient(RecipientRepository recipientRepository) {
        this.recipientRepository = recipientRepository;
    }

    public void renameRecipient(RecipientId recipientId, Name newName) {
        recipientRepository.getById(recipientId)
                           .ifPresentOrElse(recipient -> recipient.rename(newName),
                                            () -> {
                                                throw new RecipientNotFoundException(recipientId);
                                            });
    }
}
