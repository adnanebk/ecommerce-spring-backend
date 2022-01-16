package com.adnanbk.ecommerceang.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Data
public class ApiError {
    private Integer status;
    private String message;
    private Set errors;

    public ApiError(Integer status, String message, Set errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(String message) {
        this(HttpStatus.BAD_REQUEST.value(), message, null);

    }

/*    public ApiError(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }*/
}
