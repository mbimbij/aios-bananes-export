package com.example.aiosbananesexport.infra.in;

import lombok.Data;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class BusinessErrorDto {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss XXXX");
    private String timestamp;
    private String message;
    private String debugMessage;
    private List<BusinessSubErrorDto> subErrors;

    private BusinessErrorDto() {
        timestamp = ZonedDateTime.now().format(TIMESTAMP_FORMATTER);
    }

    public BusinessErrorDto(Throwable ex) {
        this();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public BusinessErrorDto(String message, Throwable ex) {
        this();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    /**
     * For testing purpose
     *
     * @param message
     * @param ex
     * @param timestamp to ensure timestamps match
     */
    public BusinessErrorDto(String message, Throwable ex, ZonedDateTime timestamp) {
        this.timestamp = timestamp.format(TIMESTAMP_FORMATTER);
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
