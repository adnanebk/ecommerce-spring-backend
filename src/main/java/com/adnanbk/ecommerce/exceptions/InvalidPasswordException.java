package com.adnanbk.ecommerce.exceptions;

import lombok.Getter;

@Getter
public class InvalidPasswordException extends  RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }

}
