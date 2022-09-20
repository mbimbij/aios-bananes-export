package com.example.aiosbananesexport.common;

import com.example.aiosbananesexport.domain.BusinessException;

public interface SelfValidating {
    void validate() throws BusinessException;
}
