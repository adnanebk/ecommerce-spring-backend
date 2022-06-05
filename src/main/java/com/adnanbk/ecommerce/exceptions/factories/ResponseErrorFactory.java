package com.adnanbk.ecommerce.exceptions.factories;

import com.adnanbk.ecommerce.dto.ResponseError;
import com.adnanbk.ecommerce.utils.ErrorMessagesUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Component
public class ResponseErrorFactory {

 private ErrorMessagesUtil errorMessagesUtil;

    public ResponseErrorFactory(ErrorMessagesUtil errorMessagesUtil) {
        this.errorMessagesUtil = errorMessagesUtil;
    }


    public  ResponseError create(String uniqueErrorMessage) {
           String message = this.errorMessagesUtil.getDefaultMessage("error.already-exist");
        if (uniqueErrorMessage.toLowerCase().contains("uniqueName".toLowerCase()))
            return create("name", message);
        if (uniqueErrorMessage.toLowerCase().contains("uniqueCardNumber".toLowerCase()))
            return create("cardNumber", message);
        if (uniqueErrorMessage.toLowerCase().contains("uniqueSku".toLowerCase()))
            return create("sku", message);
        if (uniqueErrorMessage.toLowerCase().contains("uniqueEmail".toLowerCase()))
            return create("email", message);
        return null;
    }

    public  ResponseError create(String fieldName, String errorMessage) {
        String formattedName = fieldName.equals(fieldName.toLowerCase()) ? fieldName : formatToWordsWithSpaces(fieldName);

        return new ResponseError(fieldName, formattedName+" " +errorMessage);
    }

    private  String formatToWordsWithSpaces(String fieldName) {
        return StringUtils
                .uncapitalize(fieldName.replaceAll("([a-z])([A-Z]+)", "$1 $2").toLowerCase());

    }




}
