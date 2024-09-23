package com.adnanbk.ecommerce.utils;

import com.adnanbk.ecommerce.exceptions.InvalidTokenException;
import com.adnanbk.ecommerce.models.ConfirmationToken;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConfirmationTokenUtil {


    private ConfirmationTokenUtil() {
    }

    private static final Map<String, ConfirmationToken> tokensUsers = new ConcurrentHashMap<>();


    public static void verifyToken(String email, String token) {
        var confirmationToken = tokensUsers.get(email);
        if (confirmationToken == null || !confirmationToken.token().equals(token))
            throw new InvalidTokenException("Invalid code");
        if (confirmationToken.expirationDate().isBefore(LocalDate.now()))
            throw new InvalidTokenException("The code is expired");
    }

    public static void seTokenForUser(String email, String token) {
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDate.now().plusDays(1));
        tokensUsers.put(email, confirmationToken);
    }


}
