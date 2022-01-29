package com.adnanbk.ecommerce.dto;

import org.springframework.util.StringUtils;

public class ResponseErrorFactory {

    private ResponseErrorFactory() {
    }

    public static ResponseError createResponseError(String fieldName, String errorMessage) {
        String formattedName = fieldName.equals(fieldName.toLowerCase()) ? fieldName : formatToWordsWithSpaces(fieldName);

        return new ResponseError(fieldName, formattedName, errorMessage);
    }

    private static String formatToWordsWithSpaces(String fieldName) {
        return StringUtils
                .uncapitalize(fieldName.replaceAll("([a-z])([A-Z]+)", "$1 $2").toLowerCase());

    }

    public static ResponseError createResponseError(String errorMessage) {
        String subKey = "uniquekey";
        int startIndex = errorMessage.indexOf(subKey);
        int endIndex = errorMessage.indexOf("__");
        if (startIndex != -1 && endIndex != -1) {
            String fieldName = errorMessage.substring(startIndex + subKey.length() + 1, endIndex);
            return createResponseError(fieldName, "already exists");

        }
        return null;
    }


}
