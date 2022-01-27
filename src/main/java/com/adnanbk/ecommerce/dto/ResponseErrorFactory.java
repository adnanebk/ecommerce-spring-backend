package com.adnanbk.ecommerce.dto;

public class ResponseErrorFactory {


    public static ResponseError createResponseError(String errorMessage) {
        ResponseError responseError = null;
        if (errorMessage.contains("product(name)"))
            responseError = new ResponseError("name", "already exists");
        else if (errorMessage.contains("product(sku)"))
            responseError = new ResponseError("sku", "already exists");

        else if (errorMessage.contains("product_category(name)"))
            responseError = new ResponseError("name", "already exists");

        else if (errorMessage.contains("credit_card(card_number)"))
            responseError = new ResponseError("cardNumber", "already exists");

        else if (errorMessage.contains("user(user_name)"))
            responseError = new ResponseError("userName", "already exists");

        else if (errorMessage.contains("user(email)"))
            responseError = new ResponseError("email", "already exists");

        return responseError;
    }

}
