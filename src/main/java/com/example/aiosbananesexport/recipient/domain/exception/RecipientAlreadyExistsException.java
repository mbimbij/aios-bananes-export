package com.example.aiosbananesexport.recipient.domain.exception;

import com.example.aiosbananesexport.common.DomainException;
import com.example.aiosbananesexport.recipient.domain.entity.Address;
import com.example.aiosbananesexport.recipient.domain.entity.Name;

public class RecipientAlreadyExistsException extends DomainException {
    public RecipientAlreadyExistsException(Name name, Address address) {
        super("recipient already exists. name: %s, address:%s".formatted(name, address));
    }
}
