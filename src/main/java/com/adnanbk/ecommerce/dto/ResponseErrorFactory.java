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

        ResponseError responseError = null;
        String meggage = "already exists";
        if (errorMessage.contains("product(name)"))
            responseError = createResponseError("name", meggage);
        else if (errorMessage.contains("product(sku)"))
            responseError = createResponseError("sku", meggage);

        else if (errorMessage.contains("product_category(name)"))
            responseError = createResponseError("name", meggage);

        else if (errorMessage.contains("credit_card(card_number)"))
            responseError = createResponseError("cardNumber", meggage);

        else if (errorMessage.contains("user(user_name)"))
            responseError = createResponseError("userName", meggage);

        else if (errorMessage.contains("user(email)"))
            responseError = createResponseError("email", meggage);

        return responseError;
    }


}
