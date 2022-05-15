package com.adnanbk.ecommerce.exceptions;

import lombok.Getter;

@Getter
public class InvalidPasswordException extends  RuntimeException {
    private final String fieldName="currentPassword";
    public InvalidPasswordException(String message) {
        super(message);
    }

}
