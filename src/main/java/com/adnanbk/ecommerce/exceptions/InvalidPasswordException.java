package com.adnanbk.ecommerce.exceptions;

import lombok.Getter;

@Getter
public class InvalidPasswordException extends  RuntimeException {
    private String fieldName;
    public InvalidPasswordException(String message, String fieldName) {
        super(message);
        this.fieldName=fieldName;

    }

}
