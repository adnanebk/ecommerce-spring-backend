package com.adnanbk.ecommerce.exceptions.factories;

import com.adnanbk.ecommerce.dto.ResponseError;
import com.adnanbk.ecommerce.utils.StringUtil;


public class ResponseErrorFactory {
    private ResponseErrorFactory() {
    }

    public static ResponseError create(String uniqueMessage, Exception uniqueErrorException) {
        String message = uniqueErrorException.getMessage();
        if (message.toLowerCase().contains("uniqueName".toLowerCase()))
            return create("name", uniqueMessage);
        if (message.toLowerCase().contains("uniqueCardNumber".toLowerCase()))
            return create("cardNumber", uniqueMessage);
        if (message.toLowerCase().contains("uniqueSku".toLowerCase()))
            return create("sku", uniqueMessage);
        if (message.toLowerCase().contains("uniqueEmail".toLowerCase()))
            return create("email", uniqueMessage);
        return null;
    }

    public static   ResponseError create(String fieldName, String errorMessage) {
        if(fieldName.contains("."))
            fieldName=fieldName.substring(fieldName.lastIndexOf(".")+1);
        String formattedName = StringUtil.camelCaseWordsToWordsWithSpaces(fieldName);
        return new ResponseError(fieldName, formattedName+" " +errorMessage);
    }
    public static   ResponseError create(Object rootBean, String fieldName, String errorMessage) {
        if(fieldName.contains("."))
            fieldName=fieldName.substring(fieldName.lastIndexOf(".")+1);
        String formattedName = StringUtil.camelCaseWordsToWordsWithSpaces(fieldName);
        return new ResponseError(rootBean,fieldName, formattedName+" " +errorMessage);
    }




}
