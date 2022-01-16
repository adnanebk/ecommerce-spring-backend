package com.adnanbk.ecommerce.exceptions;

public class CustomFileException extends RuntimeException {
    public CustomFileException() {
        super();
    }


    public CustomFileException(String message) {
        super(message);
    }
}
