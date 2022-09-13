package com.example.aiosbananesexport.recipient.domain.exception;

import com.example.aiosbananesexport.common.DomainException;
import com.example.aiosbananesexport.recipient.domain.entity.Address;
import com.example.aiosbananesexport.recipient.domain.entity.Name;
import com.example.aiosbananesexport.recipient.domain.entity.RecipientId;

public class RecipientNotFoundException extends DomainException {
    public RecipientNotFoundException(RecipientId recipientId) {
        super("Recipient not found for id: %s".formatted(recipientId.getValue()));
    }

    public RecipientNotFoundException(Name name, Address address) {
        super("Recipient not found. name: %s, address: %s".formatted(name, address));
    }
}
