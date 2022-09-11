package com.example.aiosbananesexport.recipient.exception;

import com.example.aiosbananesexport.common.DomainException;
import com.example.aiosbananesexport.recipient.domain.RecipientId;

public class RecipientNotFoundException extends DomainException {
    public RecipientNotFoundException(RecipientId recipientId) {
        super("Recipient not found for id: %s".formatted(recipientId.getValue()));
    }
}
