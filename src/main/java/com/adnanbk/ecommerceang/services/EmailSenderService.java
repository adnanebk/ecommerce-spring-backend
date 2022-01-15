package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.models.AppUser;

public interface EmailSenderService {

    void sendEmailConfirmation(String email);

    void verifyToken(String token);
}
