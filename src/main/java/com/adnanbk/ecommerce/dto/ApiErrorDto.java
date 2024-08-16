package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ApiErrorDto {
    private String message;
    private String code;
    private Set<ResponseError> errors;

    public ApiErrorDto(String message, Set<ResponseError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDto(String message) {
        this(message, new HashSet<>());

    }
    public ApiErrorDto(String message,String code) {
        this(message, new HashSet<>());
        this.code=code;

    }
}
