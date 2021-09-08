package com.adnanbk.ecommerceang.services;

import com.adnanbk.ecommerceang.dto.JwtResponse;

import java.security.SecureRandom;

public interface SocialService {
    JwtResponse verify(JwtResponse jwtResponse);

    // Method to generate a random alphanumeric password of a specific length
    public default String generateRandomPassword(int len)
    {
        // ASCII range – alphanumeric (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        // each iteration of the loop randomly chooses a character from the given
        // ASCII range and appends it to the `StringBuilder` instance

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
