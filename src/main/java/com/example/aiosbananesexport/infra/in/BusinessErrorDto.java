package com.example.aiosbananesexport.infra.in;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class BusinessErrorDto {
    private static DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
    private HttpStatus status;
    private String timestamp;
    private String message;
    private String debugMessage;
    private List<BusinessSubErrorDto> subErrors;

    private BusinessErrorDto() {
        timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
    }

    public BusinessErrorDto(HttpStatus status) {
        this();
        this.status = status;
    }

    public BusinessErrorDto(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public BusinessErrorDto(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
