package com.adnanbk.ecommerce.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
@Setter
public class ApiErrorDto {
    private Integer status;
    private String message;
    private Set<?> errors;

    public ApiErrorDto(Integer status, String message, Set<?> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiErrorDto(String message) {
        this(HttpStatus.BAD_REQUEST.value(), message, null);

    }

}
