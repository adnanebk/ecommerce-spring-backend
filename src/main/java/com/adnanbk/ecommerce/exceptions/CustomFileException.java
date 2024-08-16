package com.adnanbk.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomFileException extends RuntimeException {
    public CustomFileException() {
        super();
    }


    public CustomFileException(String message) {
        super(message);
    }
}
