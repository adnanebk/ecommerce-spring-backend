package com.adnanbk.ecommerce.utils;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public  interface PasswordUtil {

    static String generateRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        IntStream.range(0,6)
                .forEach(number->
                        sb.append(chars.charAt(random.nextInt(chars.length())))
                );
        return sb.toString();
    }
}
