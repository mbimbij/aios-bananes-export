package com.example.aiosbananesexport.utils;

import java.util.function.Function;

public class Uncheck {
    private Uncheck() {
    }

    public static <T, R, E extends Exception> Function<T, R> uncheck(ThrowingFunction<T, R, E> fn) {
        return t -> {
            try {
                return fn.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
