package com.adnanbk.ecommerce.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductSkuAlreadyExistException extends RuntimeException {

    public ProductSkuAlreadyExistException(String message) {
        super(message);
    }
}
