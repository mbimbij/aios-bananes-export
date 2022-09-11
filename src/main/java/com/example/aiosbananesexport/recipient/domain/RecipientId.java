package com.example.aiosbananesexport.recipient.domain;

import com.example.aiosbananesexport.common.Id;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class RecipientId extends Id<String> {
    public RecipientId(String value) {
        super(value);
    }
}
