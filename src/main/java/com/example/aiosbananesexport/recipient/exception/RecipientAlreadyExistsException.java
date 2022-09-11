package com.example.aiosbananesexport.recipient.exception;

import com.example.aiosbananesexport.common.DomainException;
import com.example.aiosbananesexport.recipient.domain.Address;
import com.example.aiosbananesexport.recipient.domain.Name;

public class RecipientAlreadyExistsException extends DomainException {
    public RecipientAlreadyExistsException(Name name, Address address) {
        super("recipient already exists. name: %s, address:%s".formatted(name, address));
    }
}
