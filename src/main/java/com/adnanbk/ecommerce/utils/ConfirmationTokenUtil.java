package com.adnanbk.ecommerce.utils;

import com.adnanbk.ecommerce.models.AppUser;
import com.adnanbk.ecommerce.models.ConfirmationToken;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConfirmationTokenUtil {


    private ConfirmationTokenUtil() {
    }

    private static final Map<AppUser, ConfirmationToken> usersTokens = new ConcurrentHashMap<>();
    private static final Map<String, ConfirmationToken> tokensUsers = new ConcurrentHashMap<>();

    public static ConfirmationToken getConfirmationToken(AppUser appUser){
        return usersTokens.get(appUser);
    }
    public static ConfirmationToken getConfirmationToken(String token){
        return tokensUsers.get(token);
    }
    public static ConfirmationToken setConfirmationTokenForUser(AppUser appUser){
        String token= UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDate.now().plusDays(1),appUser);
         usersTokens.put(appUser,confirmationToken);
         tokensUsers.put(token,confirmationToken);
         return confirmationToken;
    }


}
