package com.adnanbk.ecommerce.dto;

import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
import org.springframework.util.StringUtils;

public class ResponseErrorFactory {

    private ResponseErrorFactory() {
    }


    public static ResponseError createResponseError(String errorMessage, ErrorMessagesUtil errorMessagesUtil) {
           String message = errorMessagesUtil.getDefaultMessage("error.already-exist");
        if (errorMessage.toLowerCase().contains("uniqueName".toLowerCase()))
            return createResponseError("name", message);
        if (errorMessage.toLowerCase().contains("uniqueCardNumber".toLowerCase()))
            return createResponseError("cardNumber", message);
        if (errorMessage.toLowerCase().contains("uniqueSku".toLowerCase()))
            return createResponseError("sku", message);
        return null;
    }

    public static ResponseError createResponseError(String fieldName, String errorMessage) {
        String formattedName = fieldName.equals(fieldName.toLowerCase()) ? fieldName : formatToWordsWithSpaces(fieldName);

        return new ResponseError(fieldName, formattedName+" " +errorMessage);
    }

    private static String formatToWordsWithSpaces(String fieldName) {
        return StringUtils
                .uncapitalize(fieldName.replaceAll("([a-z])([A-Z]+)", "$1 $2").toLowerCase());

    }




}
