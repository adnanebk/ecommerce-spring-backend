package com.adnanbk.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException() {
        super();
    }


    public CardNotFoundException(String message) {
        super(message);
    }
}
