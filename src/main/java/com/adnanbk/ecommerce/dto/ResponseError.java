package com.adnanbk.ecommerce.dto;

public record ResponseError (Object rootBean,String fieldName,String message)
{
    public ResponseError(String fieldName, String message) {
        this(null, fieldName, message);
    }
}
