package com.adnanbk.ecommerce.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException() {
        super("this user is not enabled yet ,please check your email account ,we have send you a confirmation email");
    }


    public UserNotEnabledException(String message) {
        super(message);
    }


}
