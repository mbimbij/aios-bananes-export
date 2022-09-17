package com.example.aiosbananesexport.infra.in;

import java.time.format.DateTimeFormatter;

public class DateTimeFormat {
    public static final String DATE_PATTERN = "dd-MM-yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
}
