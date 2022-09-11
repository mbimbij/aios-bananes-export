package com.example.aiosbananesexport.common;

public abstract class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
