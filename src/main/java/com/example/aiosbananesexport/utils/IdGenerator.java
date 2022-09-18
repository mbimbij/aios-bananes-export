package com.example.aiosbananesexport.utils;

import java.util.UUID;
import java.util.function.Supplier;

public class IdGenerator {
    private static final Supplier<String> DEFAULT_ID_SUPPLIER = () -> UUID.randomUUID().toString();
    private static Supplier<String> idSupplier = DEFAULT_ID_SUPPLIER;

    public static String newId() {
        return idSupplier.get();
    }

    public static void useFixedId(String fixedId) {
        idSupplier = () -> fixedId;
    }
}
