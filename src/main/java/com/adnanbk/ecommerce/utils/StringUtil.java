package com.adnanbk.ecommerce.utils;

import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public interface StringUtil {

    static String generateRandomPassword() {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return generateString(chars, 6);
    }

    private static String generateString(String chars, int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        IntStream.rangeClosed(0, length)
                .forEach(number ->
                        sb.append(chars.charAt(random.nextInt(chars.length())))
                );
        return sb.toString();
    }

    static String camelCaseWordsToWordsWithSpaces(String str) {
        if (StringUtils.hasLength(str))
            return str.toLowerCase().equals(str) ? str : StringUtils
                    .uncapitalize(str.replaceAll("([a-z])([A-Z]+)", "$1 $2").toLowerCase());
        return str;
    }

    static String generateCode(int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        return generateString(chars, length);
    }
}
