package com.adnanbk.ecommerce.services;

public interface EmailSenderService {

    void sendEmailConfirmation(String email);

    void verifyToken(String token);
}
