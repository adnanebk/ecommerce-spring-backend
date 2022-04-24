package com.adnanbk.ecommerce.services;

public interface EmailSenderService {

    void sendEmailConfirmation(String rooUrl,String email);

    String enableUser(String token);
}
