package com.adnanbk.ecommerce.exceptions;


public class UserNotEnabledException extends RuntimeException {
    public UserNotEnabledException() {
        super("this user is not enabled yet ,please check your email account ,we have send you a confirmation email");
    }
    public UserNotEnabledException(String message) {
        super(message);
    }


}
