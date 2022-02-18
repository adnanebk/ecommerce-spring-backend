package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ApiErrorDto {
    private String message;
    private Set<?> errors;

    public ApiErrorDto( String message, Set<ResponseError> errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDto(String message) {
        this(message, null);

    }

}
