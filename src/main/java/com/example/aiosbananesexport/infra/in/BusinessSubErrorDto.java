package com.example.aiosbananesexport.infra.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class BusinessSubErrorDto {
    private String object;
    private String message;
}
