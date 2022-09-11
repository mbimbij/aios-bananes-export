package com.example.aiosbananesexport.common;

import java.time.format.DateTimeFormatter;

public class DateFormat {
    public static final String dateFormatPatternString = "dd-MM-yyyy";
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormatPatternString);
}
