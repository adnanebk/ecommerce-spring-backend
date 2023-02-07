package com.adnanbk.ecommerce.utils;

import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.stream.IntStream;

public  interface StringUtil {

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

    static   String camelCaseWordsToWordsWithSpaces(String str) {
        if(StringUtils.hasLength(str))
          return str.toLowerCase().equals(str)?str:StringUtils
                .uncapitalize(str.replaceAll("([a-z])([A-Z]+)", "$1 $2").toLowerCase());
        return str;
    }
}
